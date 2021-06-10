package com.unhas.ac.id.roomdb.crud.mytask.viewmodel

import android.provider.SyncStateContract.Helpers.insert
import android.provider.SyncStateContract.Helpers.update
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.unhas.ac.id.roomdb.crud.mytask.db.Task
import com.unhas.ac.id.roomdb.crud.mytask.db.TaskEvent
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
    val inputDesc = MutableLiveData<String>()
    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()
    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<TaskEvent<String>>()

    val message: LiveData<TaskEvent<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        if (taskIsUpdateDelete) {
            taskToUpdateDelete.name = inputName.value!!
            taskToUpdateDelete.desc = inputDesc.value !!
            update(taskToUpdateDelete)
        } else {
            val name = inputName.value!!
            val desc = inputDesc.value!!



            insert(Task(0, name, desc))
        }
        inputName.value = null
        inputDesc.value = null


    }

    fun clearAllOrDelete() {
        if (taskIsUpdateDelete) {
            delete(taskToUpdateDelete)
        } else {
            clearAll()
        }
    }

    fun insert(task: Task) = viewModelScope.launch {
        val newRows = repository.insert(task)
        if (newRows> - 1) {
            statusMessage.value = TaskEvent("Task inserted successfully $newRows")
        } else {
            statusMessage.value = TaskEvent("Error occurred!")
        }
    }

    private fun taskForms() {
        inputName.value = null
        inputDesc.value = null
        taskIsUpdateDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }


    fun update(task: Task) = viewModelScope.launch {
        val numbOfUp = repository.update(task)
        if (numbOfUp > 0) {
            taskForms()

            statusMessage.value = TaskEvent("$numbOfUp Task updated successfully")
        } else {

            statusMessage.value = TaskEvent("Error occurred!")
        }

    }

    fun delete(task: Task) = viewModelScope.launch {
        val numbOfDel = repository.delete(task)
        if (numbOfDel > 0) {
            taskForms()

            statusMessage.value = TaskEvent("$numbOfDel Task deleted successfully")
        } else {

            statusMessage.value = TaskEvent("Error occurred!")
        }
    }

    fun clearAll() = viewModelScope.launch {
        val numbOfClr = repository.deleteAll()
        if (numbOfClr > 0) {
            statusMessage.value = TaskEvent("$numbOfClr Task deleted successfully")
        } else {
            statusMessage.value = TaskEvent("Error occurred!")
        }
    }

    fun initUpdateAndDelete(task: Task) {
        inputName.value = task.name
        inputDesc.value = task.desc
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