package com.todo.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.todo.R
import com.todo.databinding.FragmentSettingsBinding
import java.util.Locale

class SettingsFragment:Fragment() {
    lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSettingsBinding.inflate(inflater,container,false)




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languagesDropDown()
        modesDropDown()
        binding.autoCompleteTVLanguages.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    setLocale("en")
                    binding.autoCompleteTVLanguages.setText("English")



                }
                1 -> {
                    setLocale("ar")
                    binding.autoCompleteTVLanguages.setText("Arabic")

                }


            }
        }
        binding.autoCompleteTVModes.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {setMode(AppCompatDelegate.MODE_NIGHT_NO)
                    binding.autoCompleteTVModes.setText(R.string.light)
                }
                1 ->{ setMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.autoCompleteTVModes.setText(R.string.light)}
            }
        }
        updateStartIcon()









    }



    private fun modesDropDown() {
        val modes = resources.getStringArray(R.array.modes)
        val modesArrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, modes)
        binding.autoCompleteTVModes.setAdapter(modesArrayAdapter)
    }




    private fun languagesDropDown() {
        val languages=resources.getStringArray(R.array.languages)
        val languageArrayAdapter=ArrayAdapter(requireContext(),R.layout.drop_down_item,languages)
        binding.autoCompleteTVLanguages.setAdapter(languageArrayAdapter)


    }
    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
        requireActivity().recreate()
    }
    private fun setMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
        requireActivity().recreate()
    }
    private fun updateStartIcon() {
        val mode = resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)

        val startIconDrawable = when (mode) {
            Configuration.UI_MODE_NIGHT_NO -> R.drawable.ic_light_mode
            Configuration.UI_MODE_NIGHT_YES -> R.drawable.ic_dark
            else -> R.drawable.ic_light_mode
        }

        binding.modeTil.startIconDrawable = ContextCompat.getDrawable(requireContext(), startIconDrawable)
    }

}