package info.stefancojocaru.wikisearch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.widget.Toast
import info.stefancojocaru.wikisearch.room.DbWorkerThread
import info.stefancojocaru.wikisearch.room.SearchDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    private var lista: ArrayList<Model.Search>? = null

    private lateinit var mDbWorkerThread: DbWorkerThread
    private var mDb : SearchDatabase?=null
    private val mUiHandler = Handler()


    private val wikiApiServe by lazy {
        WikiApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

        btn_search.setOnClickListener {
            if (edit_search.text.toString().isNotEmpty()) {
                cautare(edit_search.text.toString())
            }
        }
        btn_insertdb.setOnClickListener {
            testInsert()
        }
        btn_retrievedb.setOnClickListener {
            testRetrieve()
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

    //INSERT INTO ROOM DATABASE
    private fun testInsert(){

        val search = Model.Search(1,1,"test",1,150,"Room library test","2018-09-12")
        val task = Runnable{
            mDb?.searchDao()?.insert(search)
        }
        mDbWorkerThread.postTask(task)
    }

    //RETRIEVE FROM ROOM DATABASE
    private fun testRetrieve(){
        val task = Runnable {
            val weatherData =
                    mDb?.searchDao()?.getAll()
            mUiHandler.post({
                if (weatherData == null || weatherData?.size == 0) {

                    Toast.makeText(this,"No data in cache..!!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,weatherData?.get(0).toString(),Toast.LENGTH_LONG).show()
                }
            })
        }
        mDbWorkerThread.postTask(task)
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}