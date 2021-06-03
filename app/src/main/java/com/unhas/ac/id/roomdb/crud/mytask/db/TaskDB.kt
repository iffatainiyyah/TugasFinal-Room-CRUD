package com.unhas.ac.id.roomdb.crud.mytask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class TaskDB : RoomDatabase() {

    abstract val taskDAO: TaskDAO

    companion object {
        @Volatile
        private var INSTANCE: TaskDB? = null
        fun getInstance(context: Context): TaskDB {
            synchronized(this) {
                var instance: TaskDB? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDB::class.java,
                        "task_database"
                    ).build()
                }
                return instance
            }
        }
    }
}