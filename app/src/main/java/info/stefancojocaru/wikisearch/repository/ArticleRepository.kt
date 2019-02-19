package info.stefancojocaru.wikisearch.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import info.stefancojocaru.wikisearch.model.Model
import info.stefancojocaru.wikisearch.retrofit.WikiApiService
import info.stefancojocaru.wikisearch.room.ArticleDao_Impl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository {

    lateinit var articleDao: ArticleDao_Impl
    lateinit var allArticles: MutableLiveData<List<Model.Result>>

    fun doSearch(): MutableLiveData<List<Model.Result>> {
        WikiApiService.create().fetchArticles("query",
                "json","search","bmw")  //last parameter represents the search query (can be passed from anywhere)
                .enqueue(object : Callback<List<Model.Result>>{
                    override fun onFailure(call: Call<List<Model.Result>>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<List<Model.Result>>, 
                                            response: Response<List<Model.Result>>
                                           ) {
                        allArticles.value = response.body()
                    }
                })
        return allArticles

    }
}
