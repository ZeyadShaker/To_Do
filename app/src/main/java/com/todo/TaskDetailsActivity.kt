package com.todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.content.IntentCompat
import com.todo.database.TasksDatabase
import com.todo.databinding.ActivityTaskDetailsBinding
import com.todo.fragments.TasksFragment
import com.todo.models.Task
import java.util.Calendar

class TaskDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityTaskDetailsBinding
    lateinit var calendar: Calendar
    var onSaveButtonAddedListener: OnTaskAddedListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val task = IntentCompat.getParcelableExtra(intent, "task", Task::class.java)
        task?.let { task ->

            showData(task)

        }
        calendar = Calendar.getInstance()
        binding.saveButton.setOnClickListener {
            if (validateFields()) {
                calendar.set(Calendar.HOUR, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)

                val modifyTask = task?.copy(
                    title = binding.title.text.toString(),
                    description = binding.description.text.toString(),
                    date = calendar.time, isDone = false
                )
                TasksDatabase.getInstance(this).getTasksDao().insertTask(modifyTask!!)

                showData(modifyTask)
                onSaveButtonAddedListener?.onTaskAdded()


                finish()


            }
        }


        binding.time.setOnClickListener {
            val picker = DatePickerDialog(
                this, object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(
                        view: DatePicker?,
                        year: Int,
                        month: Int,
                        dayOfMonth: Int
                    ) {
                        binding.time.setText("$dayOfMonth/${month + 1}/$year")
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, month)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)


                    }

                }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            picker.show()
        }
    }

    private fun validateFields(): Boolean {
        if (binding.title.text?.isEmpty() == true || binding.title.text?.isBlank() == true) {
            binding.titleTil.error = "Empty or Blank field"
            return false
        } else {
            binding.titleTil.error = null
        }
        if (binding.description.text?.isEmpty() == true || binding.description.text?.isBlank() == true) {
            binding.descriptionTil.error = "empty or blank field"
            return false
        } else {
            binding.descriptionTil.error = null
        }
        if (binding.time.text!!.equals(getString(R.string.select_time))) {
            binding.timeTil.error = "empty or blank field"
            return false
        } else {
            binding.timeTil.error = null
        }




        return true
    }
    fun showData(task: Task){
        binding.title.setText(task.title)
        binding.description.setText(task.description)
        binding.time.setText(task.date.toString())    }

}


