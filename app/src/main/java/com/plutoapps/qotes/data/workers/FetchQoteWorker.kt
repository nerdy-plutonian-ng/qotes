package com.plutoapps.qotes.data.workers

import android.content.Context
import android.graphics.BitmapFactory
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.WorkerParameters
import com.plutoapps.qotes.R
import com.plutoapps.qotes.data.repositories.QoteApi
import com.plutoapps.qotes.data.repositories.QotesRepo
import com.plutoapps.qotes.ui.utils.Notify

class FetchQoteWorker(private val context : Context, params : WorkerParameters) : CoroutineWorker(context,params){
    override suspend fun doWork(): Result {
        return try {
            val qotes = QoteApi.retrofitService.getQote("happiness")
            Notify().createNotification("Your daily Qote",qotes.first().quote, context)
            Result.success()
        } catch (throwable: Throwable) {
            Result.failure()
        }
    }




}