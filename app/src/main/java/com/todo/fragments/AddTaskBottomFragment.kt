package com.todo.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.todo.OnTaskAddedListener
import com.todo.R
import com.todo.database.TasksDatabase
import com.todo.databinding.FragmentAddTaskBinding
import com.todo.models.Task
import java.util.Calendar

class AddTaskBottomFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddTaskBinding
    lateinit var calendar: Calendar
    var onTaskAddedListener:OnTaskAddedListener?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar=Calendar.getInstance()
        binding.addTaskBtn.setOnClickListener {
            if (validateFields()) {
                calendar.set(Calendar.HOUR,0)
                calendar.set(Calendar.MINUTE,0)
                calendar.set(Calendar.SECOND,0)
                calendar.set(Calendar.MILLISECOND,0)
                TasksDatabase.getInstance(requireContext()).getTasksDao().insertTask(Task(
                    title = binding.title.text.toString(),
                    description = binding.description.text.toString(),
                    date = calendar.time,
                    isDone = false,


                ))
                onTaskAddedListener?.onTaskAdded()
                dismiss()

            }
        }
        binding.selectTimeTv.setOnClickListener {
            val picker =
                TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                        binding.selectTimeTv.text = "$hourOfDay:$minute"
                    }

                }, 0, 0, false)
            picker.show()
        }
        binding.selectDateTv.setOnClickListener{
            val picker=DatePickerDialog(requireContext(),object :DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    binding.selectDateTv.text="$dayOfMonth/${month + 1}/$year"
                    calendar.set(Calendar.YEAR,year)
                    calendar.set(Calendar.MONTH,month)
                    calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)


                }

            },calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
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
        if (binding.selectDateTv.text == getString(R.string.select_date)) {
            binding.selectDateTil.error = "empty or blank field"
            return false
        } else {
            binding.selectDateTil.error = null
        }

        if (binding.selectTimeTv.text == getString(R.string.select_time)) {
            binding.selectTimeTil.error = "empty or blank field"
            return false
        } else {
            binding.selectTimeTil.error = null
        }


        return true
    }


}