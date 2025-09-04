package com.ulud.laporan_ewarga.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ulud.laporan_ewarga.ui.theme.UserRole
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

@Singleton
class UserPreferencesRepository @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val USER_ROLE = stringPreferencesKey("user_role")
    }

    val userRoleFlow: Flow<UserRole> = dataStore.data.map { preferences ->
        val roleName = preferences[PreferencesKeys.USER_ROLE] ?: UserRole.WARGA.name
        UserRole.valueOf(roleName)
    }

    suspend fun saveUserRole(userRole: UserRole) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ROLE] = userRole.name
        }
    }
}