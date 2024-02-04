package com.plutoapps.qotes.data.repositories

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.plutoapps.qotes.data.models.Qote
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

interface PrefsRepo {
    val dataStore: DataStore<Preferences>
    suspend fun saveKey(key: String)

    suspend fun getKey() : String?
    suspend fun saveTodaysQote(qote : Qote)
    suspend fun getTodaysQote() : Qote?
}

class UserPreferencesRepository(
    override val dataStore: DataStore<Preferences>
) : PrefsRepo {
    private companion object {
        val keyPrefKey = stringPreferencesKey("key")
        val qotePrefKey = stringPreferencesKey("qote")
    }

    override suspend fun saveKey(key: String) {
        dataStore.edit {
            prefs ->
            prefs[keyPrefKey] = key
        }
    }

    override suspend fun getKey(): String? {
        val preferences = dataStore.data.first()
        return preferences[keyPrefKey]
    }

    override suspend fun saveTodaysQote(qote: Qote) {
        dataStore.edit {
                prefs ->
            prefs[qotePrefKey] = Gson().toJson(qote)
        }
    }

    override suspend fun getTodaysQote(): Qote? {
        val preferences = dataStore.data.first()
        val qoteJson = preferences[qotePrefKey]
        return  if (qoteJson == null) null else Gson().fromJson(qoteJson, Qote::class.java)
    }

}