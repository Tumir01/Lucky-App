package com.example.luckyfanapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.luckyfanapp.databinding.ActivityRegistrationBinding
import com.example.luckyfanapp.model.User
import com.google.firebase.firestore.FirebaseFirestore

class RegistrationActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityRegistrationBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(bindingClass.root)

        val mDateBase = FirebaseFirestore.getInstance()

        bindingClass.apply {

            textViewIsLogin.setOnClickListener {
                val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            signUpButton.setOnClickListener {
                val login = userLoginSignUp.text.toString().trim()
                val password = userPasswordSignUp.text.toString().trim()
                val email = userEmailSignUp.text.toString().trim()

                if (login.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    Toast.makeText(this@RegistrationActivity, R.string.not_all_fields_are_filled_in, Toast.LENGTH_LONG).show()
                } else {
                    val user = User(login = login, password = password, email = email)
                    mDateBase.collection("users").add(user.toMap())
                        .addOnSuccessListener {
                            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this@RegistrationActivity, R.string.registration_failed, Toast.LENGTH_LONG).show()
                        }
                }
            }
        }
    }
}
