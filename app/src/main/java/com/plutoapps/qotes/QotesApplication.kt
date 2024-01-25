package com.plutoapps.qotes

import android.app.Application
import com.plutoapps.qotes.data.repositories.QotesDatabase
import com.plutoapps.qotes.data.repositories.QotesRepo
import com.plutoapps.qotes.data.repositories.SqlQotesRepo

class QotesApplication : Application() {

    companion object {
        var qotesRepo : QotesRepo? = null
    }

    override fun onCreate() {
        super.onCreate()
        qotesRepo = SqlQotesRepo(QotesDatabase.getDatabase(this).qotesDao())
    }

}