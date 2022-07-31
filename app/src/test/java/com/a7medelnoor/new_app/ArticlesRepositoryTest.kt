package com.a7medelnoor.new_app

import com.a7medelnoor.new_app.data.local.LocalDataSource
import com.a7medelnoor.new_app.data.remote.RemoteDataSource
import com.a7medelnoor.new_app.data.remote.api.ApiService
import com.a7medelnoor.new_app.data.remote.model.Media
import com.a7medelnoor.new_app.data.remote.model.MediaMetadata
import com.a7medelnoor.new_app.data.remote.model.NewsResponse
import com.a7medelnoor.new_app.data.remote.model.Result
import com.a7medelnoor.new_app.repository.ArticleRepository
import junit.framework.Assert
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class ArticlesRepositoryTest {
    lateinit var articlesRepository: ArticleRepository
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
    val myResponse = NewsResponse(listOf(myResult))

    @Mock
    lateinit var apiService: ApiService
    @Mock
    lateinit var remote: RemoteDataSource
    @Mock
    lateinit var local: LocalDataSource

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        articlesRepository = ArticleRepository(apiService,remote,local)
    }
    @Test
    fun `get all articles`(){
          runBlocking {
              Mockito.`when`(remote.getArticles(apikey))
                  .thenReturn(Response.success((myResponse)))
              val response = articlesRepository.remote.getArticles(apikey)
              org.junit.Assert.assertEquals(myResponse, response.body())

          }
    }
}