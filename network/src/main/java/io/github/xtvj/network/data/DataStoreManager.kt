package io.github.xtvj.network.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("settings")


data class UserPreferences(
    val userId: String,
    val token: String
)

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val dataStore = appContext.dataStore

    private object PreferencesKeys {
        val userId = stringPreferencesKey("userID")
        val token = stringPreferencesKey("token")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapUserPreferences(preferences)
        }

    suspend fun insertUser(id: String, token: String) {
        dataStore.edit {
            it[PreferencesKeys.userId] = id
            it[PreferencesKeys.token] = token
        }
    }

    suspend fun updateToken(token: String){
        dataStore.edit {
            it[PreferencesKeys.token] = token
        }
    }

    //data.first，如果是last则会产生阻塞 first也是最新数据
    suspend fun fetchInitialPreferences() =
        mapUserPreferences(dataStore.data.first().toPreferences())

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val id = preferences[PreferencesKeys.userId] ?: ""
        val token = preferences[PreferencesKeys.token] ?: ""
        return UserPreferences(id, token)
    }
}