package info.stefancojocaru.wikisearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_search.setOnClickListener {
            if (edit_search.text.toString().isNotEmpty()) {

            }
        }
        btn_insertdb.setOnClickListener {

        }
        btn_retrievedb.setOnClickListener {

        }
    }

    override fun onPause() {
        super.onPause()
    }
}