package info.stefancojocaru.wikisearch.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import info.stefancojocaru.wikisearch.Model

@Dao
interface SearchDAO {
    @Query("SELECT * from search_table")
    fun getAll(): List<Model.Search>

    @Insert(onConflict = REPLACE)
    fun insert(search: Model.Search)

    @Query("DELETE from search_table")
    fun deleteAll()
}