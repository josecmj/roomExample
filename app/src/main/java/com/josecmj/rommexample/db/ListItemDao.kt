package com.josecmj.rommexample.db


import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ListItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: ListItem)

    @Delete
    suspend fun delete(item: ListItem)

    @Delete
    fun deleteItem(item: ListItem)

    @Query("SELECT * FROM items ORDER BY name ASC")
    fun getAll() : LiveData<List<ListItem>>

    @Update
    fun update(vararg items: ListItem)

    @Query("DELETE FROM items")
    fun clearDB()

}