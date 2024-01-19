package com.raphaelsilva.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.raphaelsilva.orgs.R
import com.raphaelsilva.orgs.database.AppDatabase
import com.raphaelsilva.orgs.databinding.ActivityLoginBinding
import com.raphaelsilva.orgs.extensions.toHash
import com.raphaelsilva.orgs.preferences.dataStore
import com.raphaelsilva.orgs.preferences.userAuthenticationPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private var authenticationByFingerPrint = true

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val daoUser by lazy {
        val db = AppDatabase.instance(this)
        db.userDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        onClickSignIn()
        onClickSignUp()
    }

    private fun verifyUserAlreadyAuthenticated() {
        val userAuthenticationFlow: Flow<String> = dataStore.data.map { preferences ->
            preferences[userAuthenticationPreferences] ?: ""
        }


        lifecycleScope.launch {
            userAuthenticationFlow.collect {
                if (it.isNullOrBlank() || !authenticationByFingerPrint) return@collect
                binding.loginUsernameInput.editText?.setText(it)
                authenticateWithFingerPrint(it)

            }
        }
    }

    private fun authenticateWithFingerPrint(username: String) {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    goToHomePage()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext, "Autenticação falhou", Toast.LENGTH_SHORT
                    ).show()
                }
            })


        promptInfo = BiometricPrompt.PromptInfo.Builder().setTitle("Autenticar como $username")
            .setSubtitle("Use sua impressão digital").setNegativeButtonText("Usar senha").build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun validateFields() {
        val usernameText = binding.loginUsernameInput.editText?.text.toString()
        val passText = binding.loginPassInput.editText?.text.toString().toHash()
        lifecycleScope.launch {
            daoUser.authenticateUser(usernameText, passText)?.let { user ->
                dataStore.edit { preferences ->
                    val userAuthentication = stringPreferencesKey(USER_ID_KEY)
                    preferences[userAuthentication] = user.id
                }
                goToHomePage()
            } ?: Toast.makeText(
                this@LoginActivity, "Usuário ou senha invalido!", Toast.LENGTH_LONG
            ).show()

        }
    }

    private fun onClickSignIn() {
        val signInButton = binding.loginEnterButton
        signInButton.setOnClickListener {
            authenticationByFingerPrint = false
            validateFields()
        }
    }

    private fun onClickSignUp() {
        val signUpButton = binding.loginCreateButton
        signUpButton.setOnClickListener {
            val intent = Intent(this, CreateUserActivity::class.java)
            startActivity(intent)
            clearFields()
        }
    }

    private fun goToHomePage() {
        val intent = Intent(this, ProductListActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun clearFields() {
        binding.loginUsernameInput.editText?.setText("")
        binding.loginPassInput.editText?.setText("")
    }

}