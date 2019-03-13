package com.jjf.template.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jjf.template.result.Article

/**
 * @author xj
 * date: 18-11-8
 * description :
 */
@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articles: List<Article>)

    @Query("SELECT * FROM article")
    fun loadHomeArticle():LiveData<List<Article>>

}
