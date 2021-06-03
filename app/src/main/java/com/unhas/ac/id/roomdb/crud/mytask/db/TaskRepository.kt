package com.unhas.ac.id.roomdb.crud.mytask.db

class TaskRepository(private val dao: TaskDAO) {

    val task = dao.getAllTask();

    suspend fun insert(task: Task) {
        dao.insertTask(task)
    }

    suspend fun update(task: Task) {
        dao.updateTask(task)
    }

    suspend fun delete(task: Task) {
        dao.deleteTask(task)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}