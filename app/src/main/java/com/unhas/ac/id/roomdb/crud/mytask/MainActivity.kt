package com.unhas.ac.id.roomdb.crud.mytask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.unhas.ac.id.roomdb.crud.mytask.adapter.TaskAdapter
import com.unhas.ac.id.roomdb.crud.mytask.databinding.ActivityMainBinding
import com.unhas.ac.id.roomdb.crud.mytask.db.Task
import com.unhas.ac.id.roomdb.crud.mytask.db.TaskDB
import com.unhas.ac.id.roomdb.crud.mytask.db.TaskRepository
import com.unhas.ac.id.roomdb.crud.mytask.viewmodel.TaskViewModel
import com.unhas.ac.id.roomdb.crud.mytask.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = TaskDB.getInstance(application).taskDAO
        val repository = TaskRepository(dao)
        val factory = TaskViewModelFactory(repository)
        taskViewModel = ViewModelProvider(this, factory).get(TaskViewModel::class.java)
        binding.myTask = taskViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        taskViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initRecyclerView() {
        binding.taskRV.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter({selectedItem: Task->listItemClicked(selectedItem)})
        binding.taskRV.adapter = adapter
        displayTaskList()
    }

    private fun displayTaskList() {
        taskViewModel.task.observe(this, Observer {
            Log.i("MyTaskTag", it.toString())
            adapter.setTaskList(it)
            adapter.notifyDataSetChanged()
        })
    }
    private fun listItemClicked(task: Task) {
        taskViewModel.initUpdateAndDelete(task)
    }
}