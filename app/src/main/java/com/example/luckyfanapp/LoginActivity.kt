package com.example.luckyfanapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.luckyfanapp.databinding.ActivityLoginBinding
import com.example.luckyfanapp.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var bindingClass : ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == Constants.PERMISSION_BACKGROUND) {
            val savedBackground = sharedPreferences.getInt(Constants.PERMISSION_BACKGROUND, R.drawable.main_screen_1)
            bindingClass.LoginLayout.setBackgroundResource(savedBackground)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
        val savedBackground = sharedPreferences.getInt(Constants.PERMISSION_BACKGROUND, R.drawable.main_screen_1)
        bindingClass.LoginLayout.setBackgroundResource(savedBackground)
    }
}