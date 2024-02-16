package com.todo.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Contacts.Intents
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.todo.OnClickListener
import com.todo.TaskDetailsActivity
import com.todo.adapters.TasksAdapter
import com.todo.database.TasksDatabase
import com.todo.databinding.FragmentTasksBinding
import com.todo.models.Task
import java.util.Calendar

class TasksFragment:Fragment() {
    lateinit var binding: FragmentTasksBinding
    lateinit var adapter: TasksAdapter
    lateinit var calendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentTasksBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= TasksAdapter(null)
        binding.rvTasks.adapter=adapter
       getTodos()
        calendar=Calendar.getInstance()
        binding.calenderView.setOnDateChangedListener { widget, date, selected ->
            calendar.set(Calendar.HOUR,0)
            calendar.set(Calendar.MINUTE,0)
            calendar.set(Calendar.SECOND,0)
            calendar.set(Calendar.MILLISECOND,0)
            val year=date.year
            val month=date.month - 1
            val dayOfMonth=date.day
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,month)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            val tasks=TasksDatabase.getInstance(requireContext()).getTasksDao().getTasksInDate(calendar.time)
            adapter.updateData(tasks)


        }
        adapter.onDeletedClickListener=object :OnClickListener{
            override fun onClick(item: Task, position: Int) {
                val tasks=TasksDatabase.getInstance(requireContext()).getTasksDao().deleteTask(item)
                adapter.notifyItemRemoved(position)



            }


        }
        adapter.onTaskClickListener=object :OnClickListener{
            override fun onClick(item: Task, position: Int) {
                val intent= Intent(requireContext(),TaskDetailsActivity::class.java)
                intent.putExtra("task",item)




                requireContext().startActivity(intent)
            }

        }



    }

     @SuppressLint("NotifyDataSetChanged")
     fun getTodos() {
        val tasks=TasksDatabase.getInstance(requireContext()).getTasksDao().getAllTasks()
        adapter.updateData(tasks)
         adapter.notifyDataSetChanged()

    }

    override fun onResume() {
        super.onResume()
        getTodos()
    }

}