package com.example.luckyfanapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
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

        setDefaultValues()

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
                val intent = Intent(this@SettingsActivity, GameActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        val savedBackground = sharedPreferences.getInt("background", R.drawable.main_screen_1)
        bindingClass.settingsLinearLayout.setBackgroundResource(savedBackground)
    }

    private fun setDefaultValues() {
        val defaultDifficulty = "medium"
        val defaultTime = 10000

        when (sharedPreferences.getString("difficulty", defaultDifficulty)) {
            "low" -> bindingClass.lowRB.isChecked = true
            "medium" -> bindingClass.mediumRB.isChecked = true
            "hard" -> bindingClass.hardRB.isChecked = true
        }

        when (sharedPreferences.getInt("time", defaultTime)) {
            5000 -> bindingClass.fiveSecRB.isChecked = true
            10000 -> bindingClass.tenSecRB.isChecked = true
            20000 -> bindingClass.twentySecRB.isChecked = true
        }
    }
}
