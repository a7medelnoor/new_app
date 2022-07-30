package com.a7medelnoor.new_app

import com.a7medelnoor.new_app.data.remote.api.ApiService
import com.a7medelnoor.new_app.repository.ArticleRepository
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class ApiServiceTest {

    lateinit var mockWebServer : MockWebServer
    lateinit var apiService: ApiService
    lateinit var gson: Gson
     var apikey = "2oaHHG65c7UUwJmTGR0AyXCz4PCPuQ1N"
    @Mock
    lateinit var articleRepository: ArticleRepository

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        gson = Gson()
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiService::class.java)
    }
    @Test
    fun `get all articles api test`(){
        runBlocking {
            val mockResponse = MockResponse()
            mockWebServer.enqueue(mockResponse.setBody("[]"))
            val response = apiService.getNewsArticles(apikey)
            val request = mockWebServer.takeRequest()
            Assert.assertEquals("/movielist.json",request.path)
            Assert.assertEquals(true,response.body()?.results?.isEmpty()==true)
        }
    }
    @After
    fun teardown(){
        mockWebServer.shutdown()
    }

}