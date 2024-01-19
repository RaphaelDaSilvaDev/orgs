package com.raphaelsilva.orgs.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.raphaelsilva.orgs.ui.activity.USER_ID_KEY

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "authenticated_user")
val userAuthenticationPreferences = stringPreferencesKey(USER_ID_KEY)