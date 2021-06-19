package com.josecmj.rommexample.model

import android.content.Context
import android.os.AsyncTask
import com.josecmj.rommexample.db.ListItem
import com.josecmj.rommexample.db.ListItemDao
import com.josecmj.rommexample.db.ListItemDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class DataSourceRoom(val context: Context) : IDataSource {

    val itemDao: ListItemDao = ListItemDatabase.getInstance(context).listItemDao

    override val itemsLiveData =  itemDao.getAll()

    override fun addItem(itemName: String, itemValue: String) {
        val item = ListItem(itemName,itemValue)
        //itemDao.insert(item)
        InsertAsyncTask(itemDao).execute(item)
    }

    suspend fun deleteItemCoroutine(item: ListItem) {
        withContext(Dispatchers.IO) {
            itemDao.delete(item)
        }
    }

    override fun deleteItem(item: ListItem) {
        itemDao.deleteItem(item)
    }

    companion object {
        private var INSTANCE: DataSourceRoom? = null

        fun getDataSource(context: Context): DataSourceRoom {
            return synchronized(DataSourceRoom::class) {
                val newInstance = INSTANCE ?: DataSourceRoom(context)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
    class InsertAsyncTask(val itemDao: ListItemDao): AsyncTask<ListItem, Unit, Unit>() {
        override fun doInBackground(vararg p0: ListItem?){
            p0[0]?.let { itemDao.insert(it) }
        }
    }

}



