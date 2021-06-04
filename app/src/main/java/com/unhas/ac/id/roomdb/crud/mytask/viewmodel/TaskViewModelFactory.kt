package com.unhas.ac.id.roomdb.crud.mytask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unhas.ac.id.roomdb.crud.mytask.db.TaskRepository

class TaskViewModelFactory(private val repository: TaskRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}