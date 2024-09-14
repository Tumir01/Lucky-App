package com.example.luckyfanapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luckyfanapp.databinding.ActivityLoginBinding
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        val mDateBase = FirebaseFirestore.getInstance()

        bindingClass.apply {
            loginButton.setOnClickListener {
                val login = userLogin.text.toString().trim()
                val password = userPassword.text.toString().trim()

                if (login.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this@LoginActivity, R.string.not_all_fields_are_filled_in, Toast.LENGTH_LONG).show()
                } else {
                    mDateBase.collection("users").whereEqualTo("login", login).get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val documents = task.result
                                var userFound = false
                                for (document in documents) {
                                    val user = document.data
                                    val passwordStored = user["password"] as? String
                                    if (passwordStored == password) {
                                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                        return@addOnCompleteListener
                                    } else {
                                        Toast.makeText(this@LoginActivity, R.string.incorrect_password, Toast.LENGTH_LONG).show()
                                        return@addOnCompleteListener
                                    }
                                }
                                Toast.makeText(this@LoginActivity, R.string.user_is_not_found, Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this@LoginActivity, R.string.error_getting_documents, Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }
            dontHaveAnAccount.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
                startActivity(intent)
                finish()
            }

            dontLoginButton.setOnClickListener {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
