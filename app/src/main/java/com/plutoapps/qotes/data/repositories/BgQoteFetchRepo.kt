package com.plutoapps.qotes.data.repositories

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.plutoapps.qotes.data.workers.FetchQoteWorker
import java.util.Date
import java.util.concurrent.TimeUnit

class BgQoteFetchRepo(context : Context, private val desiredExecutionTime : Long) {

    private val workManager = WorkManager.getInstance(context)
    fun fetchQote(){
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val fetchQoteBuilder = PeriodicWorkRequestBuilder<FetchQoteWorker>(24,TimeUnit.HOURS).
        setInitialDelay(desiredExecutionTime - Date().time,TimeUnit.MILLISECONDS)
            .setConstraints(constraints).setInputData(inputData = Data.Builder().putLong("time",desiredExecutionTime).build())
        workManager.enqueue(fetchQoteBuilder.build())
    }
}