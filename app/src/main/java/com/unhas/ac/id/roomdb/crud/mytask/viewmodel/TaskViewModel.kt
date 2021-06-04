package com.unhas.ac.id.roomdb.crud.mytask.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unhas.ac.id.roomdb.crud.mytask.db.Task
import com.unhas.ac.id.roomdb.crud.mytask.db.TaskRepository
import kotlinx.coroutines.launch

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
        val name = inputName.value!!
        val date = inputDate.value!!

        insert(Task(0, name, date))

        inputName.value = null
        inputDate.value = null
    }

    fun clearAllOrDelete() {
        clearAll()
    }

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository.delete(task)
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