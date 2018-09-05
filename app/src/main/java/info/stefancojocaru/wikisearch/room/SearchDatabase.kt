package info.stefancojocaru.wikisearch.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import info.stefancojocaru.wikisearch.Model

@Database(entities = arrayOf(Model.Search::class), version = 1)
abstract class SearchDatabase : RoomDatabase() {

    abstract fun searchDao() : SearchDAO

    companion object {
        private var INSTANCE: SearchDatabase? = null

        fun getInstance(context: Context): SearchDatabase? {
            if (INSTANCE == null) {
                synchronized(SearchDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SearchDatabase::class.java, "search.db")
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