package com.smaedev.todolist.task

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.smaedev.todolist.AppDatabase
import com.smaedev.todolist.MainActivity
import com.smaedev.todolist.R
import com.smaedev.todolist.databinding.ActivityAddTaskBinding
import com.smaedev.todolist.entity.Task

class AddTaskActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_task)
        binding.buttonSave.setOnClickListener { saveTask() }
    }

    private fun saveTask() {
        val sTask = binding.editTextTask.text.toString().trim()
        val sDesc: String = binding.editTextDesc.text.toString().trim()
        val sFinishBy: String = binding.editTextFinishBy.text.toString().trim()

        if (sTask.isEmpty()) {
            binding.editTextTask.error = applicationContext.getString(R.string.title_required)
            binding.editTextTask.requestFocus()
            return
        }
        if (sDesc.isEmpty()) {
            binding.editTextDesc.error = applicationContext.getString(R.string.description_required)
            binding.editTextDesc.requestFocus()
            return
        }
        if (sFinishBy.isEmpty()) {
            binding.editTextFinishBy.error = applicationContext.getString(R.string.delay_required)
            binding.editTextFinishBy.requestFocus()
            return
        }
        class SaveTask : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void? {
                //creating a task
                val task = Task(taskTitle = sTask, description = sDesc, finishBy = sFinishBy, isFinished = false)

                //adding to database
                AppDatabase.getDatabase(application)
                    .taskDao()
                    .insert(task)
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                finish()
                startActivity(Intent(applicationContext, MainActivity::class.java))
                Toast.makeText(applicationContext, applicationContext.getString(R.string.saved), Toast.LENGTH_LONG).show()
            }
        }

        val st = SaveTask()
        st.execute()
    }

}