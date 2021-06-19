package com.josecmj.rommexample

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.josecmj.rommexample.db.ListItem
import com.josecmj.rommexample.model.DataSourceFirebase
import com.josecmj.rommexample.model.IDataSource
import kotlinx.coroutines.launch

class ListItemViewModel(val dataSource: IDataSource) : ViewModel() {
    fun addItem(itemName: String, itemValue: String) {
        dataSource.addItem(itemName,itemValue)
    }

    fun deleteItem(item: ListItem) {
        viewModelScope.launch {
            dataSource.deleteItem(item)
        }
    }

    val itemsLiveData = dataSource.itemsLiveData
}

class ListItemViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListItemViewModel(
                //dataSource = DataSourceRoom.getDataSource(context)
                dataSource = DataSourceFirebase.getDataSource(context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}