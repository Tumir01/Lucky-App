package com.example.luckyfanapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luckyfanapp.databinding.ActivityInfoBinding

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