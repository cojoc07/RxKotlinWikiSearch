package info.stefancojocaru.wikisearch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    private var lista: ArrayList<Model.Search>? = null

    private val wikiApiServe by lazy {
        WikiApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_search.setOnClickListener {
            if (edit_search.text.toString().isNotEmpty()) {
                cautare(edit_search.text.toString())
            }
        }
    }

    private fun cautare(searchString: String) {

        disposable = wikiApiServe.hitCountCheck("query", "json", "search", searchString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result ->
                                txt_search_result.text = "${result.query.searchinfo.totalhits} aparitii gasite."
                                lista = result.query.search.toCollection(ArrayList())
                                listArticles()

                        },
                        {
                            error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                        }
                )
    }

    private fun listArticles(){

        txt_rezultate.text = ""
        disposable?.dispose()

        disposable = Observable.fromIterable(lista)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result -> txt_rezultate.text = txt_rezultate.text.toString()+"\n"+
                                    result.title +"\n" + Html.fromHtml(result.snippet)+"\n\n"
                        },
                        {
                            error -> Toast.makeText(this,error.message,Toast.LENGTH_LONG).show()
                        }
                )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}