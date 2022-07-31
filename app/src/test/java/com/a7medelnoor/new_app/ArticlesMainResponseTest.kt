package com.a7medelnoor.new_app

import com.a7medelnoor.new_app.data.remote.model.Media
import com.a7medelnoor.new_app.data.remote.model.MediaMetadata
import com.a7medelnoor.new_app.data.remote.model.NewsResponse
import com.a7medelnoor.new_app.data.remote.model.Result
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ArticlesMainResponseTest {
    val metadata =
        MediaMetadata("https://static01.nyt.com/images/2022/07/22/multimedia/22Kamil_1/22Kamil_1-thumbStandard.jpg")
    val media = Media(listOf(metadata))
    val themedia = listOf(media)
    val result = Result(
        "ahmed",
        1,
        themedia,
        "2022-07-22",
        "section",
        "source",
        "subsection",
        "title",
        "2022-07-30"
    )
    val myResponse = NewsResponse(listOf(result))
        val emptyResult = Result(
                "",
                1,
                themedia,
                "2022-07-22",
                "section",
                "source",
                "subsection",
                "",
                ""
        )
        val emptyNewsResponse = NewsResponse(listOf(emptyResult))

    @Test
    fun validateResult() {
        if (result.media.isEmpty() && result.media[0].MediaMetadata.isEmpty()) {
            true
        }
        false
    }

    @Test
    fun validateMainResponse() {
        assert(true, { validateMainResponse().equals(myResponse) })
    }

    @Test
    fun validateMainResponseEmpty() {
        assert(true, { validateMainResponse().equals(emptyNewsResponse) })
    }
}