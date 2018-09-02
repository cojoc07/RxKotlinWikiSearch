package info.stefancojocaru.wikisearch

object Model {
    data class Result(val query: Query)
    data class Query(val searchinfo: SearchInfo, val search: Array<Search>)
    data class SearchInfo(val totalhits: Int)
    data class Search(val ns: Int, val title: String, val pageid: Int, val size: Int, val wordcount: Int,
                      val snippet: String, val timestamp: String)
}