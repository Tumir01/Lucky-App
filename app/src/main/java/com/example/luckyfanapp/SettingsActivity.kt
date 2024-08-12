package com.example.luckyfanapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.luckyfanapp.Constants.FIVE_SECS_TIMER
import com.example.luckyfanapp.Constants.HARD_DIFFICULT
import com.example.luckyfanapp.Constants.LOW_DIFFICULT
import com.example.luckyfanapp.Constants.MEDIUM_DIFFICULT
import com.example.luckyfanapp.Constants.PERMISSION_BACKGROUND
import com.example.luckyfanapp.Constants.PERMISSION_DIFFICULTY
import com.example.luckyfanapp.Constants.PERMISSION_TIME
import com.example.luckyfanapp.Constants.SHARED_PREFERENCES_NAME
import com.example.luckyfanapp.Constants.TEN_SECS_TIMER
import com.example.luckyfanapp.Constants.TWENTY_SECS_TIMER
import com.example.luckyfanapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivitySettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        setDefaultValues()

        bindingClass.apply {
            radioGroupBackground.setOnCheckedChangeListener { _, checkedId ->
                val editor = sharedPreferences.edit()
                when (checkedId) {
                    R.id.backgroundFirstRB -> {
                        settingsLinearLayout.setBackgroundResource(R.drawable.main_screen_1)
                        editor.putInt(PERMISSION_BACKGROUND, R.drawable.main_screen_1)
                    }
                    R.id.backgroundSecondRB -> {
                        settingsLinearLayout.setBackgroundResource(R.drawable.main_screen_2)
                        editor.putInt(PERMISSION_BACKGROUND, R.drawable.main_screen_2)
                    }
                }
                editor.apply()
            }

            radioGroupTime.setOnCheckedChangeListener { _, checkedId ->
                val editor = sharedPreferences.edit()
                when (checkedId) {
                    R.id.fiveSecRB -> {
                        editor.putInt(PERMISSION_TIME, FIVE_SECS_TIMER)
                    }
                    R.id.tenSecRB -> {
                        editor.putInt(PERMISSION_TIME, TEN_SECS_TIMER)
                    }
                    R.id.twentySecRB -> {
                        editor.putInt(PERMISSION_TIME, TWENTY_SECS_TIMER)
                    }
                }
                editor.apply()
            }

            radioGroupDifficulty.setOnCheckedChangeListener { _, checkedId ->
                val editor = sharedPreferences.edit()
                var newDifficulty: String? = null
                var shouldShowTimeBG = true

                when (checkedId) {
                    R.id.lowRB -> {
                        newDifficulty = LOW_DIFFICULT
                        shouldShowTimeBG = false
                    }
                    R.id.mediumRB -> {
                        newDifficulty = MEDIUM_DIFFICULT
                    }
                    R.id.hardRB -> {
                        newDifficulty = HARD_DIFFICULT
                    }
                }

                val currentDifficulty = sharedPreferences.getString(PERMISSION_DIFFICULTY, "")
                if (currentDifficulty != newDifficulty) {
                    editor.putString(PERMISSION_DIFFICULTY, newDifficulty)
                    editor.apply()
                }

                bindingClass.timeBG.visibility = if (shouldShowTimeBG) View.VISIBLE else View.INVISIBLE
            }
        }

        val savedBackground = sharedPreferences.getInt(PERMISSION_BACKGROUND, R.drawable.main_screen_1)
        bindingClass.settingsLinearLayout.setBackgroundResource(savedBackground)

        bindingClass.backButton2.setOnClickListener {
            val intent = Intent(this@SettingsActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setDefaultValues() {
        val defaultDifficulty = MEDIUM_DIFFICULT
        val defaultTime = TEN_SECS_TIMER
        val defaultBackground = R.drawable.main_screen_1

        when (sharedPreferences.getInt(PERMISSION_BACKGROUND, defaultBackground)) {
            R.drawable.main_screen_1 -> bindingClass.backgroundFirstRB.isChecked = true
            R.drawable.main_screen_2 -> bindingClass.backgroundSecondRB.isChecked = true
        }


        when (sharedPreferences.getString(PERMISSION_DIFFICULTY, defaultDifficulty)) {
            LOW_DIFFICULT -> { bindingClass.lowRB.isChecked = true
                bindingClass.timeBG.visibility = View.INVISIBLE }
            MEDIUM_DIFFICULT -> bindingClass.mediumRB.isChecked = true
            HARD_DIFFICULT -> bindingClass.hardRB.isChecked = true
        }

        when (sharedPreferences.getInt(PERMISSION_TIME, defaultTime)) {
            FIVE_SECS_TIMER -> bindingClass.fiveSecRB.isChecked = true
            TEN_SECS_TIMER -> bindingClass.tenSecRB.isChecked = true
            TWENTY_SECS_TIMER -> bindingClass.twentySecRB.isChecked = true
        }
    }
}
