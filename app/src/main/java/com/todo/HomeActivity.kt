 package com.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.todo.databinding.ActivityHomeBinding
import com.todo.fragments.AddTaskBottomFragment
import com.todo.fragments.SettingsFragment
import com.todo.fragments.TasksFragment

 class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var tasksFragment: TasksFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.tasks ->{
                    tasksFragment=TasksFragment()
                     pushFragment(tasksFragment)

                }
                R.id.settings -> {
                    pushFragment(SettingsFragment())

                }
            }

return@setOnItemSelectedListener true
        }
        binding.bottomNavigation.selectedItemId=R.id.tasks
        binding.fabAddTask.setOnClickListener {
            val bottomSheetFragment=AddTaskBottomFragment()
            bottomSheetFragment.onTaskAddedListener=object :OnTaskAddedListener{
                override fun onTaskAdded() {
                    if (tasksFragment.isVisible)
                    tasksFragment.getTodos()


                }


            }

            bottomSheetFragment.show(supportFragmentManager,null)
        }


    }

     private fun pushFragment(fragment: Fragment) {
         supportFragmentManager.beginTransaction().replace(binding.content.fragmentContainer.id,fragment).commit()

     }
 }