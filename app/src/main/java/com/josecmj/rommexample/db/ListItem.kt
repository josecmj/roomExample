package com.josecmj.rommexample.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "items")
data class ListItem(
    @PrimaryKey var name:String,
    var price:String?) {

    constructor():this("name","value"){

    }
}