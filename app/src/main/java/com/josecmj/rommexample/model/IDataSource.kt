package com.josecmj.rommexample.model

import androidx.lifecycle.LiveData
import com.josecmj.rommexample.db.ListItem

interface IDataSource {
    val itemsLiveData: LiveData<List<ListItem>>
    fun addItem(itemName: String, itemValue: String)
    fun deleteItem(item: ListItem)
}