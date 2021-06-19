package com.josecmj.rommexample.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ListItem::class], version = 1, exportSchema = false)
abstract class ListItemDatabase : RoomDatabase() {
    abstract val listItemDao : ListItemDao

    companion object {
        @Volatile
        private var INSTANCE: ListItemDatabase? = null

        fun getInstance(context: Context) : ListItemDatabase{
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ListItemDatabase::class.java,
                        "grocery_list_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}