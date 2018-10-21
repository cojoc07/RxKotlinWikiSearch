package info.stefancojocaru.wikisearch.model

import androidx.room.Entity
import androidx.room.PrimaryKey

object Model {
    data class Result(val query: Query)
    data class Query(val searchinfo: SearchInfo, val articles: Array<Article>)
    data class SearchInfo(val totalhits: Int)

    @Entity(tableName = "articles_table")
    data class Article(
            @PrimaryKey(autoGenerate = true)
            val pageid: Int,
            val ns: Int,
            val title: String,
            val size: Int,
            val wordcount: Int,
            val snippet: String,
            val timestamp: String)
}