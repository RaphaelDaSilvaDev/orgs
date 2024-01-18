package com.raphaelsilva.orgs.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.raphaelsilva.orgs.R
import com.raphaelsilva.orgs.database.AppDatabase
import com.raphaelsilva.orgs.databinding.ActivityCreateUserBinding
import com.raphaelsilva.orgs.exeptions.CoroutineException
import com.raphaelsilva.orgs.model.User
import kotlinx.coroutines.launch

class CreateUserActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCreateUserBinding.inflate(layoutInflater)
    }

    private val daoUser by lazy {
        val db = AppDatabase.instance(this)
        db.userDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar Usuário"
        onCreateUser()
    }

    private fun onCreateUser() {
        val signUpButton = binding.createUserCreateButton
        signUpButton.setOnClickListener {
            val user = user()
            lifecycleScope.launch(CoroutineException.handler(this)) {
                try {
                    daoUser.save(user)
                    finish()
                } catch (e: Exception) {
                    Log.e("UserSignUp", "onCreateUser: ", e)
                    Toast
                        .makeText(this@CreateUserActivity, "Usuário Existente", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    fun user(): User {
        val username = binding.createUserUsername.editText?.text.toString()
        val name = binding.createUserName.editText?.text.toString()
        val pass = binding.createUserPass.editText?.text.toString()

        return User(
            username,
            name,
            pass
        )
    }
}