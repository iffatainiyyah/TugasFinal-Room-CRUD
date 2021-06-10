package com.unhas.ac.id.roomdb.crud.mytask.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "task_table")
data class Task (
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    var Id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "desc")
    var desc: String



)