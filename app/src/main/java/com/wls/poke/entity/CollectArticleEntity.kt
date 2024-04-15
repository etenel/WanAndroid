package com.wls.poke.entity


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CollectArticleEntity(
    @SerialName("curPage")
    val curPage: Int = 0,
    @SerialName("datas")
    val datas: List<Data> = listOf(),
    @SerialName("offset")
    val offset: Int = 0,
    @SerialName("over")
    val over: Boolean = false,
    @SerialName("pageCount")
    val pageCount: Int = 0,
    @SerialName("size")
    val size: Int = 0,
    @SerialName("total")
    val total: Int = 0
) {
    @Keep
    @Serializable
    data class Data(
        @SerialName("author")
        val author: String = "",
        @SerialName("chapterId")
        val chapterId: Int = 0,
        @SerialName("chapterName")
        val chapterName: String = "",
        @SerialName("courseId")
        val courseId: Int = 0,
        @SerialName("desc")
        val desc: String = "",
        @SerialName("envelopePic")
        val envelopePic: String = "",
        @SerialName("id")
        val id: Int = 0,
        @SerialName("link")
        val link: String = "",
        @SerialName("niceDate")
        val niceDate: String = "",
        @SerialName("origin")
        val origin: String = "",
        @SerialName("originId")
        val originId: Int = -1,
        @SerialName("publishTime")
        val publishTime: Long = 0,
        @SerialName("title")
        val title: String = "",
        @SerialName("userId")
        val userId: Int = 0,
        @SerialName("visible")
        val visible: Int = 0,
        @SerialName("zan")
        val zan: Int = 0
    )
}