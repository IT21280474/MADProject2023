package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        emailFocusListener()

        supportActionBar?.title="Login"

        binding.registerTV.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }


        binding.loginBtn.setOnClickListener{
            val email = binding.emailLogin.text.toString()
            val password = binding.passwordLogin.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty())
                MainActivity.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                    if(it.isSuccessful){
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }
                }.addOnFailureListener{
                    Toast.makeText(this,it.localizedMessage, Toast.LENGTH_LONG).show()
                }
        }


    }
//    private fun emailFocusListener(){
//        binding.emailEditText.setOnFocusChangeListener{_,focused->
//            if (!focused){
//                binding.emailLogin.helperText=validEmail()
//            }
//        }
//    }
}