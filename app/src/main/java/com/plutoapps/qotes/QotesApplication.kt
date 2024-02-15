package com.plutoapps.qotes

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.plutoapps.qotes.data.repositories.QoteApi
import com.plutoapps.qotes.data.repositories.QotesDatabase
import com.plutoapps.qotes.data.repositories.QotesRepo
import com.plutoapps.qotes.data.repositories.SqlQotesRepo
import com.plutoapps.qotes.data.repositories.UserPreferencesRepository

private const val QOTE_PREFERENCE_NAME = "qote_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = QOTE_PREFERENCE_NAME
)

class QotesApplication : Application() {

    companion object {
        var qotesRepo : QotesRepo? = null
        var userPreferencesRepository : UserPreferencesRepository? = null
    }

    override fun onCreate() {
        super.onCreate()
        qotesRepo = SqlQotesRepo(QotesDatabase.getDatabase(this).qotesDao(),QoteApi.retrofitService)
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}