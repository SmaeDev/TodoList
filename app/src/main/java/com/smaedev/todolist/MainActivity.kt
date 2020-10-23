package com.smaedev.todolist

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smaedev.todolist.adapter.TasksAdapter
import com.smaedev.todolist.databinding.ActivityMainBinding
import com.smaedev.todolist.entity.Task
import com.smaedev.todolist.task.AddTaskActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        recyclerView = binding.recyclerviewTasks
        recyclerView.layoutManager = LinearLayoutManager(this)

        binding.floatingButtonAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
            startActivity(intent)
        }

        getTasks()
    }

    private fun getTasks() {
        class GetTasks :
            AsyncTask<Void?, Void?, List<Task>>() {
            override fun doInBackground(vararg params: Void?): List<Task>? {
                return AppDatabase
                    .getDatabase(application)
                    .taskDao()
                    .all
            }

            override fun onPostExecute(tasks: List<Task>) {
                super.onPostExecute(tasks)
                val adapter = TasksAdapter(this@MainActivity, tasks)
                recyclerView.adapter = adapter
            }
        }

        val gt = GetTasks()
        gt.execute()
    }
}
