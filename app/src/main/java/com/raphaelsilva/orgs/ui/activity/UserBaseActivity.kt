package com.raphaelsilva.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.raphaelsilva.orgs.database.AppDatabase
import com.raphaelsilva.orgs.model.User
import com.raphaelsilva.orgs.preferences.dataStore
import com.raphaelsilva.orgs.preferences.userAuthenticationPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

abstract class UserBaseActivity : AppCompatActivity() {
    private val daoUser by lazy {
        val db = AppDatabase.instance(this)
        db.userDao()
    }

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    protected val user: StateFlow<User?> = _user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            verifyIfUserAlreadyAuthenticated()
        }
    }

    private suspend fun verifyIfUserAlreadyAuthenticated() {
        dataStore.data.collect { preferences ->
            preferences[userAuthenticationPreferences]?.let { userId ->
                getUser(userId)
            } ?: goToLoginPage()
        }
    }

    private suspend fun getUser(userId: String): User? {
        return daoUser.getUser(userId).firstOrNull().also {
            _user.value = it
        }
    }


    protected fun logout() {
        lifecycleScope.launch {
            dataStore.edit { preferences ->
                preferences.clear()
            }
        }
    }

    private fun goToLoginPage() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)
    }
}