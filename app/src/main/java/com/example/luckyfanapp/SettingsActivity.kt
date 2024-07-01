package com.example.luckyfanapp

import android.os.Bundle
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.luckyfanapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivitySettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

        bindingClass.apply {
            radioGroupBackground.setOnCheckedChangeListener { _, checkedId ->
                val editor = sharedPreferences.edit()
                when (checkedId) {
                    R.id.backgroundFirstRB -> {
                        settingsLinearLayout.setBackgroundResource(R.drawable.main_screen_1)
                        editor.putInt("background", R.drawable.main_screen_1)
                    }
                    R.id.backgroundSecondRB -> {
                        settingsLinearLayout.setBackgroundResource(R.drawable.main_screen_2)
                        editor.putInt("background", R.drawable.main_screen_2)
                    }
                }
                editor.apply()
            }

            radioGroupTime.setOnCheckedChangeListener { _, checkedId ->
                val editor = sharedPreferences.edit()
                when (checkedId) {
                    R.id.fiveSecRB -> {
                        editor.putInt("time", 5000)
                    }
                    R.id.tenSecRB -> {
                        editor.putInt("time", 10000)
                    }
                    R.id.twentySecRB -> {
                        editor.putInt("time", 20000)
                    }
                }
                editor.apply()
            }

            radioGroupDifficulty.setOnCheckedChangeListener { _, checkedId ->
                val editor = sharedPreferences.edit()
                when (checkedId) {
                    R.id.lowRB -> {
                        editor.putString("difficulty", "low")
                    }
                    R.id.mediumRB -> {
                        editor.putString("difficulty", "medium")
                    }
                    R.id.hardRB -> {
                        editor.putString("difficulty", "hard")
                    }
                }
                editor.apply()
            }
        }

        val savedBackground = sharedPreferences.getInt("background", R.drawable.main_screen_1)
        bindingClass.settingsLinearLayout.setBackgroundResource(savedBackground)
    }
}
