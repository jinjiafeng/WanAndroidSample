package com.jjf.template.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jjf.template.result.Article
import com.jjf.template.result.Category

/**
 * @author xj
 * date: 18-11-8
 * description :
 */
@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProject(articles: List<Article>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(categories: List<Category>)

    @Query("SELECT * FROM article WHERE superChapterId =:cid")
    fun loadHomeArticle(cid:Int):LiveData<List<Article>>


    @Query("SELECT id,name FROM category")
    fun loadHomeCategory():LiveData<List<Category>>
}
