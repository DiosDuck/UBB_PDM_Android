package com.example.ubb_pdm_android.todo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="items")
data class Item(
    @PrimaryKey @ColumnInfo(name="_id") val _id: String,
    @ColumnInfo(name="tea") var tea: String,
    @ColumnInfo(name="type") var type: String
) {
    override fun toString(): String = tea
}
