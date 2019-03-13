package com.jjf.template.result

import androidx.room.Entity

/**
 * @author xj
 * date: 19-3-13
 * description :
 */


data class ArticleList(
    val `data`: Data,
    val errorCode: Int,
    val errorMsg: String
)

data class Data(
        val curPage: Int,
        val datas: List<Article>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int
)

@Entity(primaryKeys = ["id"])
data class Article(
    val apkLink: String,
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val projectLink: String,
    val publishTime: Long,
    val superChapterId: Int,
    val superChapterName: String,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)
