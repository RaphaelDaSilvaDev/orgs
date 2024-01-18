package com.raphaelsilva.orgs.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.raphaelsilva.orgs.R
import com.raphaelsilva.orgs.database.AppDatabase
import com.raphaelsilva.orgs.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    private val binding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val daoUser by lazy{
        val db = AppDatabase.instance(this)
        db.userDao()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        onClickSignIn()
        onClickSignUp()
    }

    private fun onClickSignIn(){
        val signInButton = binding.loginEnterButton
        signInButton.setOnClickListener {
            val intent = Intent(this, ProductListActivity::class.java)
            startActivity(intent)
        }
    }
    private fun onClickSignUp(){
        val signUpButton = binding.loginCreateButton
        signUpButton.setOnClickListener {
            val intent = Intent(this, CreateUserActivity::class.java)
            startActivity(intent)
        }
    }

}