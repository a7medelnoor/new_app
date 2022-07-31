package com.a7medelnoor.new_app.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a7medelnoor.new_app.data.local.ArticlesEntity
import com.a7medelnoor.new_app.data.remote.model.NewsResponse
import com.a7medelnoor.new_app.repository.ArticleRepository
import com.a7medelnoor.new_app.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel
@Inject constructor(
    private val articleRepository: ArticleRepository, application: Application
) : AndroidViewModel(application) {
    private val TAG = "ArticleViewModel"
    var networkStatus = false
    var context: Context? = null

    // RETROFIT mutable live data
    val articleResponse: MutableLiveData<NetworkResult<NewsResponse>> = MutableLiveData()
    val articleResponseData : MutableLiveData<List<NewsResponse>> = MutableLiveData()
    // get articles
    @RequiresApi(Build.VERSION_CODES.M)
    fun getArticles(apikey: String) = viewModelScope.launch {
        getArticlesSafeCall(apikey)
    }

    // get articles safe call
    @RequiresApi(Build.VERSION_CODES.M)
    private suspend fun getArticlesSafeCall(apikey: String) {
        articleResponse.value = NetworkResult.LOADING()
        // check for internet connection
        if (hasInternetConnection()) {
            try {
                val response = articleRepository.remote.getArticles(apikey)
                Log.d(TAG, "HomeFragment response " + response.toString())
                // handle articles response
                articleResponse.value = handleArticleResponse(response)
                Log.d(TAG, "HomeFragment article response value " + articleResponse.value)

                val articlesData = articleResponse.value!!.data
                // check if there is a new api data
                if (articlesData != null) {
                    // cache the data
                    offlineCacheArticles(articlesData)
                }

            } catch (e: Exception) {
                articleResponse.value = NetworkResult.ERROR("articles not found" + e.message)
                Log.d(TAG, "HomeFragment articles error exception " + e.message)
            }
        } else {
            articleResponse.value = NetworkResult.ERROR("No internet connection")
        }
    }

    // local cache articles data
    private fun offlineCacheArticles(articlesData: NewsResponse) {
        val articlesEntity = ArticlesEntity(articlesData)
        insertArticlesData(articlesEntity)
    }

    val readArticlesDataFromDataBse: LiveData<List<ArticlesEntity>> =
        articleRepository.local.readArticlesDataBase()

    // insert articles data to local database
    private fun insertArticlesData(articlesEntity: ArticlesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            articleRepository.local.insertArticlesToTheDataBase(articlesEntity)
        }

    // handle article multiple response type
    private fun handleArticleResponse(response: Response<NewsResponse>): NetworkResult<NewsResponse>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.ERROR("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.ERROR("API Key Limited")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.ERROR("articles Not Found")
            }
            response.isSuccessful -> {
                val articlesDataSuccess = response.body()
                return NetworkResult.SUCCESS(articlesDataSuccess!!)
            }
            else -> {
                return NetworkResult.ERROR(response.message())
            }
        }
    }

    // check if there is an internet connection
    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false

        }
    }
}