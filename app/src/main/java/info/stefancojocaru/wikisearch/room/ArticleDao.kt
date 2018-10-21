package info.stefancojocaru.wikisearch.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import info.stefancojocaru.wikisearch.model.Model

@Dao
interface ArticleDao {
    @Query("SELECT * from articles_table")
    fun getAll(): List<Model.Article>

    @Insert(onConflict = REPLACE)
    fun insert(article: Model.Article)

    @Query("DELETE from articles_table")
    fun deleteAll()
}