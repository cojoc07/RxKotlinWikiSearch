package info.stefancojocaru.wikisearch.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import info.stefancojocaru.wikisearch.model.Model

@Database(entities = arrayOf(Model.Article::class), version = 1)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun searchDao() : ArticleDao

    companion object {
        private var INSTANCE: ArticleDatabase? = null

        fun getInstance(context: Context): ArticleDatabase? {
            if (INSTANCE == null) {
                synchronized(ArticleDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ArticleDatabase::class.java, "articles.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}