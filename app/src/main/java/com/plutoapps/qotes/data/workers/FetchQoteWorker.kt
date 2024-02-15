package com.plutoapps.qotes.data.workers

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.plutoapps.qotes.QotesApplication
import com.plutoapps.qotes.data.repositories.QoteApi
import com.plutoapps.qotes.data.repositories.UserPreferencesRepository
import com.plutoapps.qotes.ui.utils.Notify
import java.util.Date
import java.util.UUID



class FetchQoteWorker(private val context : Context,val params : WorkerParameters) : CoroutineWorker(context,params){
    override suspend fun doWork(): Result {
        return try {
            val qotes = QoteApi.retrofitService.getQote("happiness")
            val qote = qotes.first().copy(id = UUID.randomUUID().toString(), date = Date().time.toString())
            val prefsRepo = QotesApplication.userPreferencesRepository
            prefsRepo!!.saveTodaysQote(qote)
            prefsRepo.setReminderTime(params.inputData.getLong("time",Date().time))
            Notify().createNotification("Your daily Qote",qote.quote, context)
            Result.success()
        } catch (throwable: Throwable) {
            Result.failure()
        }
    }
}