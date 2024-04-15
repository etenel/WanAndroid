package com.wls.poke.entity


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class HomeArticleEntity(
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
    val total: Int = 0,
) {

    @Serializable
    data class Data(
        @SerialName("adminAdd")
        val adminAdd: Boolean = false,
        @SerialName("apkLink")
        val apkLink: String = "",
        @SerialName("audit")
        val audit: Int = 0,
        @SerialName("author")
        val author: String = "",
        @SerialName("canEdit")
        val canEdit: Boolean = false,
        @SerialName("chapterId")
        val chapterId: Int = 0,
        @SerialName("chapterName")
        val chapterName: String = "",
        @SerialName("collect")
        val collect: Boolean = false,
        @SerialName("courseId")
        val courseId: Int = 0,
        @SerialName("desc")
        val desc: String = "",
        @SerialName("descMd")
        val descMd: String = "",
        @SerialName("envelopePic")
        val envelopePic: String = "",
        @SerialName("fresh")
        val fresh: Boolean = false,
        @SerialName("host")
        val host: String = "",
        @SerialName("id")
        val id: Int = 0,
        @SerialName("isAdminAdd")
        val isAdminAdd: Boolean = false,
        @SerialName("link")
        val link: String = "",
        @SerialName("niceDate")
        val niceDate: String = "",
        @SerialName("niceShareDate")
        val niceShareDate: String = "",
        @SerialName("origin")
        val origin: String = "",
        @SerialName("prefix")
        val prefix: String = "",
        @SerialName("projectLink")
        val projectLink: String = "",
        @SerialName("publishTime")
        val publishTime: Long = 0,
        @SerialName("realSuperChapterId")
        val realSuperChapterId: Int = 0,
        @SerialName("selfVisible")
        val selfVisible: Int = 0,
        @SerialName("shareDate")
        val shareDate: Long = 0,
        @SerialName("shareUser")
        val shareUser: String = "",
        @SerialName("superChapterId")
        val superChapterId: Int = 0,
        @SerialName("superChapterName")
        val superChapterName: String = "",
        @SerialName("tags")
        val tags: List<Tag> = listOf(),
        @SerialName("title")
        val title: String = "",
        @SerialName("type")
        val type: Int = 0,
        @SerialName("userId")
        val userId: Int = 0,
        @SerialName("visible")
        val visible: Int = 0,
        @SerialName("zan")
        val zan: Int = 0,
    ) {
        @Serializable
        data class Tag(
            @SerialName("name")
            val name: String = "",
            @SerialName("url")
            val url: String = "",
        )
    }
}
