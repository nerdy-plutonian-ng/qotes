package com.plutoapps.qotes.data.repositories

import com.plutoapps.qotes.data.models.Qote
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.api-ninjas.com/v1/"

val client: OkHttpClient = OkHttpClient.Builder().addInterceptor {
        val request = it.request().newBuilder().addHeader("Content-Type", "application/json")
            .addHeader("X-Api-Key", "OqCTQZtUJAQ3jZen8B3nwA==ehsqDnWK82LuT5li").build()

        it.proceed(request)
    }.build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL).client(client).build()

interface QoteApiService {
    @GET("quotes")
    suspend fun getQote(@Query("category") category: String): List<Qote>
}

object QoteApi {
    val retrofitService: QoteApiService by lazy {
        retrofit.create(QoteApiService::class.java)
    }
}

