package info.stefancojocaru.wikisearch

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

object Model {
    data class Result(val query: Query)
    data class Query(val searchinfo: SearchInfo, val search: Array<Search>)
    data class SearchInfo(val totalhits: Int)
    data class Truck(val roti : Int)

    @Entity(tableName = "search_table")
    data class Search(
            @PrimaryKey(autoGenerate = true)
            val pageid: Int,
            val ns: Int,
            val title: String,
            val size: Int,
            val wordcount: Int,
            val snippet: String,
            val timestamp: String)
}