package com.example.luckyfanapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.luckyfanapp.databinding.ActivityGameBinding
import com.example.luckyfanapp.databinding.ActivityInfoBinding
import com.example.luckyfanapp.databinding.ActivityMainBinding

class InfoActivity : AppCompatActivity() {

    private lateinit var bindingClass: ActivityInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.backButtonInfo.setOnClickListener {
            val intent = Intent(this@InfoActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}