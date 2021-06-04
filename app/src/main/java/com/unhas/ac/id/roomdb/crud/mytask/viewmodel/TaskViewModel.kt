package com.unhas.ac.id.roomdb.crud.mytask.viewmodel

import android.provider.SyncStateContract.Helpers.insert
import android.provider.SyncStateContract.Helpers.update
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unhas.ac.id.roomdb.crud.mytask.db.Task
import com.unhas.ac.id.roomdb.crud.mytask.db.TaskRepository
import kotlinx.coroutines.launch
import java.nio.file.Files.delete

class TaskViewModel(private val repository: TaskRepository): ViewModel(), Observable {

    val task= repository.task
    private var taskIsUpdateDelete = false
    private lateinit var taskToUpdateDelete: Task

    @Bindable
    val inputName = MutableLiveData<String>()
    @Bindable
    val inputDate = MutableLiveData<String>()
    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()
    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        if (taskIsUpdateDelete) {
            taskToUpdateDelete.name = inputName.value!!
            taskToUpdateDelete.date = inputDate.value !!
            update(taskToUpdateDelete)
        } else {
            val name = inputName.value!!
            val date = inputDate.value!!


            insert(Task(0, name, date))
        }
        inputName.value = null
        inputDate.value = null
    }

    fun clearAllOrDelete() {
        if (taskIsUpdateDelete) {
            delete(taskToUpdateDelete)
        } else {
            clearAll()
        }
    }

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        repository.update(task)
        inputName.value = null
        inputDate.value = null
        taskIsUpdateDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository.delete(task)
        inputName.value = null
        inputDate.value = null
        taskIsUpdateDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun initUpdateAndDelete(task: Task) {
        inputName.value = task.name
        inputDate.value = task.date
        taskIsUpdateDelete = true
        taskToUpdateDelete = task
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}