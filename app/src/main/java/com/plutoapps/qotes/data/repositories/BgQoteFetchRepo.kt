package com.plutoapps.qotes.data.repositories

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.plutoapps.qotes.data.workers.FetchQoteWorker
import java.time.Duration
import java.util.concurrent.TimeUnit

class BgQoteFetchRepo(context : Context) {

    private val workManager = WorkManager.getInstance(context)
    fun fecthQote(){
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val  testFetch = OneTimeWorkRequestBuilder<FetchQoteWorker>().setInitialDelay(5,TimeUnit.SECONDS)
        val fetchQoteBuilder = PeriodicWorkRequestBuilder<FetchQoteWorker>(24,TimeUnit.HOURS).setConstraints(constraints)
        workManager.enqueue(testFetch.build())
    }
}