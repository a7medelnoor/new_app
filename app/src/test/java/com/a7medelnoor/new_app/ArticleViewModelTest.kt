package com.a7medelnoor.new_app

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.a7medelnoor.new_app.data.local.LocalDataSource
import com.a7medelnoor.new_app.data.remote.RemoteDataSource
import com.a7medelnoor.new_app.data.remote.api.ApiService
import com.a7medelnoor.new_app.data.remote.model.Media
import com.a7medelnoor.new_app.data.remote.model.MediaMetadata
import com.a7medelnoor.new_app.data.remote.model.NewsResponse
import com.a7medelnoor.new_app.data.remote.model.Result
import com.a7medelnoor.new_app.repository.ArticleRepository
import com.a7medelnoor.new_app.util.getOrAwaitValue
import com.a7medelnoor.new_app.viewModel.ArticleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ArticleViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var articleRepository: ArticleRepository
    var apikey = "2oaHHG65c7UUwJmTGR0AyXCz4PCPuQ1N"

    val metadata =
        MediaMetadata("https://static01.nyt.com/images/2022/07/22/multimedia/22Kamil_1/22Kamil_1-thumbStandard.jpg")
    val media = Media(listOf(metadata))
    val themedia = listOf(media)
    val myResult = Result(
        "",
        1,
        themedia,
        "2022-07-22",
        "section",
        "source",
        "subsection",
        "title",
        "2022-07-30"
    )
    val myEmptyResult = Result(
        "",
        0,
        themedia,
        "",
        "",
        "",
        "",
        "",
        ""
    )
    val myResponse = NewsResponse(listOf(myResult))
    val myEmptyResponse = NewsResponse(listOf(myResult))

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var local: LocalDataSource

    @Mock
    lateinit var remote: RemoteDataSource

    @Mock
    lateinit var applicaion: Application

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        articleRepository = ArticleRepository(apiService, remote, local)
        articleViewModel = ArticleViewModel(articleRepository, applicaion)
    }

    @Test
    fun getArticles() {
        runBlocking {
            Mockito.`when`(articleRepository.remote.getArticles(apikey))
                .thenReturn(Response.success(NewsResponse(listOf(myResult))))
            articleViewModel.getArticles(apikey)
            val result = articleViewModel.articleResponse.getOrAwaitValue()
            Assert.assertEquals(listOf(NewsResponse(listOf(myResult))), result)

        }
    }
    @Test
    fun `empty news response`(){
        runBlocking {
            Mockito.`when`(articleRepository.remote.getArticles(apikey))
                .thenReturn(Response.success((myEmptyResponse)))
            articleViewModel.getArticles(apikey)
            val result = articleViewModel.articleResponse.getOrAwaitValue()
            Assert.assertEquals(NewsResponse(listOf(myEmptyResult)),result)
        }
    }
}