package com.josecmj.rommexample.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.josecmj.rommexample.db.ListItem

class DataSourceFirebase : IDataSource {
    private val db: FirebaseDatabase = Firebase.database

    val liveData:ItemListLiveData = ItemListLiveData(db.reference.child("grocery_list"))

    override fun addItem(itemName: String, itemValue: String) {
        val item = ListItem(itemName,itemValue)
        db.reference.child("grocery_list").child(item.name).setValue(item)
    }

    override fun deleteItem(item: ListItem) {
        db.reference.child("grocery_list").child(item.name).removeValue()
    }

    override val itemsLiveData: LiveData<List<ListItem>> = liveData


    companion object {
        private var INSTANCE: DataSourceFirebase? = null

        fun getDataSource(context: Context): DataSourceFirebase {
            return synchronized(DataSourceFirebase::class) {
                val newInstance = INSTANCE ?: DataSourceFirebase()
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}

class ItemListLiveData(val db: DatabaseReference) : LiveData<List<ListItem>>(emptyList()){


    val listener: ChildEventListener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val item = snapshot.getValue(ListItem::class.java)!!
            value = value!! + item
            Log.d("ZECA ", "onChildAdded")
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val item = snapshot.getValue(ListItem::class.java)!!
            value = value!! - item
            value = value!! + item
            Log.d("ZECA ", "onChildChanged")
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val item = snapshot.getValue(ListItem::class.java)!!
            value = value!! - item
            Log.d("ZECA ", "onChildRemoved")
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            Log.d("ZECA ", "onChildMoved")
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("ZECA ", "onCancelled")
        }

    }

    override fun onActive() {
        super.onActive()
        db.addChildEventListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        db.removeEventListener(listener)
    }
}