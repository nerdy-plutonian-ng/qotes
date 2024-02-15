package com.plutoapps.qotes.data.repositories

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.plutoapps.qotes.data.models.Qote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

interface PrefsRepo {
    suspend fun saveKey(key: String)

    suspend fun getKey() : String?
    suspend fun saveTodaysQote(qote : Qote)
    suspend fun getTodaysQote() : Qote?
    fun getReminderTime() : Flow<Long?>
    suspend fun setReminderTime(time : Long?)
}

class UserPreferencesRepository(
    val dataStore: DataStore<Preferences>
) : PrefsRepo {
    private companion object {
        val keyPrefKey = stringPreferencesKey("key")
        val qotePrefKey = stringPreferencesKey("qote")
        val reminderTimeKey = longPreferencesKey("reminderTime")
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
        try {
            Log.d("beesh",Gson().toJson(qote))
            dataStore.edit {
                    prefs ->
                prefs[qotePrefKey] = Gson().toJson(qote)
            }
        } catch (e:Exception){
            Log.d("beesh",e.toString())
        }
    }

    override suspend fun getTodaysQote(): Qote? {
        val preferences = dataStore.data.first()
        val qoteJson = preferences[qotePrefKey]
        return  if (qoteJson == null) null else Gson().fromJson(qoteJson, Qote::class.java)
    }

    override fun getReminderTime(): Flow<Long?> {
        return dataStore.data
            .catch {
                if(it is IOException) {
                    Log.e("Qote Prefs", "Error reading preferences.", it)
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }
            .map { it[reminderTimeKey] }
    }

    override suspend fun setReminderTime(time: Long?) {
        try {
            if(time == null){
                dataStore.edit {
                    prefs -> prefs.remove(reminderTimeKey)
                }
            } else {
                dataStore.edit {
                    prefs -> prefs[reminderTimeKey] = time
                }
            }
        } catch (_:Exception){ }
    }

}