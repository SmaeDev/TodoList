package com.smaedev.todolist.task

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.smaedev.todolist.AppDatabase
import com.smaedev.todolist.MainActivity
import com.smaedev.todolist.R
import com.smaedev.todolist.databinding.ActivityAddTaskBinding
import com.smaedev.todolist.databinding.ActivityUpdateTaskBinding
import com.smaedev.todolist.entity.Task

class UpdateTaskActivity : AppCompatActivity() {

    lateinit var binding: ActivityUpdateTaskBinding
    private var editTextTask: EditText? = null
    private var editTextDesc: EditText? = null
    private var editTextFinishBy: EditText? = null
    private var checkBoxFinished: CheckBox? = null

    var sIsFinishedBy : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_task)

        editTextTask = binding.editTextTask
        editTextDesc = binding.editTextDesc
        editTextFinishBy = binding.editTextFinishBy
        checkBoxFinished = binding.checkBoxFinished

        val taskId: Long = intent.getSerializableExtra("taskId") as Long
        val task: Task = Task(taskId)

        loadTask()
        binding.buttonUpdate.setOnClickListener {
            Toast.makeText(applicationContext, "Sélectionné", Toast.LENGTH_LONG).show()
            updateTask(task)
        }
        binding.buttonDelete.setOnClickListener {
            val builder =
                AlertDialog.Builder(this@UpdateTaskActivity)
            builder.setTitle("Êtes-vous sûr?")
            builder.setPositiveButton(
                "Oui"
            ) { dialogInterface, i -> deleteTask(task) }
            builder.setNegativeButton(
                "Non"
            ) { dialogInterface, i -> }
            val ad = builder.create()
            ad.show()
        }
    }

    private fun loadTask() {
        editTextTask!!.setText(intent.getSerializableExtra("taskTitle") as String)
        editTextDesc!!.setText(intent.getSerializableExtra("desc") as String)
        editTextFinishBy!!.setText(intent.getSerializableExtra("finishBy") as String)
        checkBoxFinished!!.isChecked = intent.getSerializableExtra("isFinished") as Boolean
    }

    private fun updateTask(task: Task) {
        val sTask = editTextTask!!.text.toString().trim()
        val sDesc: String = editTextDesc!!.text.toString().trim()
        val sFinishBy: String = editTextFinishBy!!.text.toString().trim()

        if (sTask.isEmpty()) {
            editTextTask!!.error = applicationContext.getString(R.string.title_required)
            editTextTask!!.requestFocus()
            return
        }
        if (sDesc.isEmpty()) {
            editTextDesc!!.error = applicationContext.getString(R.string.description_required)
            editTextDesc!!.requestFocus()
            return
        }
        if (sFinishBy.isEmpty()) {
            editTextFinishBy!!.error = applicationContext.getString(R.string.delay_required)
            editTextFinishBy!!.requestFocus()
            return
        }
        class UpdateTask :
            AsyncTask<Void?, Void?, Void?>() {
            @SuppressLint("WrongThread")
            override fun doInBackground(vararg params: Void?): Void? {
                task.taskTitle = sTask
                task.description = sDesc
                task.finishBy = sFinishBy
                task.isFinished = checkBoxFinished!!.isChecked

                AppDatabase.getDatabase(application)
                    .taskDao()
                    .update(task)
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                Toast.makeText(applicationContext, applicationContext.getString(R.string.updated), Toast.LENGTH_LONG).show()
                finish()
                startActivity(Intent(this@UpdateTaskActivity, MainActivity::class.java))
            }
        }

        val ut = UpdateTask()
        ut.execute()
    }


    private fun deleteTask(task: Task) {
        class DeleteTask :
            AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void? {
                AppDatabase.getDatabase(application)
                    .taskDao()
                    .delete(task)
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                Toast.makeText(applicationContext, "Supprimée", Toast.LENGTH_LONG).show()
                finish()
                startActivity(Intent(this@UpdateTaskActivity, MainActivity::class.java))
            }
        }

        val dt = DeleteTask()
        dt.execute()
    }

}