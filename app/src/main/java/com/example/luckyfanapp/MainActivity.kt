package com.example.luckyfanapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luckyfanapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindingClass : ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == "background") {
            val savedBackground = sharedPreferences.getInt("background", R.drawable.main_screen_1)
            bindingClass.mainLinearLayout.setBackgroundResource(savedBackground)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)

        bindingClass.apply {
            startButton.setOnClickListener {
                val intent = Intent(this@MainActivity, GameActivity::class.java)
                startActivity(intent)
            }
            settingsButton.setOnClickListener {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
            exitButton.setOnClickListener {
                finish()
            }
            informationButton.setOnClickListener {
                val intent = Intent(this@MainActivity, InfoActivity::class.java)
                startActivity(intent)
            }

            val savedBackground = sharedPreferences.getInt("background", R.drawable.main_screen_1)
            mainLinearLayout.setBackgroundResource(savedBackground)
        }
    }
}