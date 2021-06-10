package com.unhas.ac.id.roomdb.crud.mytask

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var adapter: TaskAdapter

    // Initialize Var
    private var bulan: Int = 0
    private var hari: Int = 0
    private var tahun: Int = 0
    private var jam: Int = 0
    private var menit: Int = 0
    private var calendar = Calendar.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val datePickerbtn: Button = findViewById(R.id.show_date_btn)
        val dateTextView: TextView = findViewById(R.id.date_edit_text)


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

        datePickerbtn.setOnClickListener {
            showDatePickerDialog()
        }

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

    private fun showDatePickerDialog() {
        hari = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        bulan = Calendar.getInstance().get(Calendar.MONTH)
        tahun = Calendar.getInstance().get(Calendar.YEAR)
        val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, tahun_muncul, bulan_muncul, hari_muncul ->
                    show_date_btn.text = ("" + hari_muncul + "/" + (bulan_muncul+1) + "/" + tahun_muncul)
                    hari = hari_muncul
                    bulan = bulan_muncul
                    tahun = tahun_muncul
                },
                tahun,
                bulan,
                hari
        )
        datePickerDialog.show()
    }




}