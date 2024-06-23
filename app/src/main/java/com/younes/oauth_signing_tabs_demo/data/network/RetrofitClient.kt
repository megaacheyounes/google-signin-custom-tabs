package com.younes.oauth_signing_tabs_demo.data.network

import com.google.gson.Gson
import com.younes.oauth_signing_tabs_demo.data.entities.GoogleErrorResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit


object RetrofitClient {

    private val logging =
        HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }

    const val TIMEOUT_SECONDS = 10L

    private val httpClient = OkHttpClient
        .Builder()
        .addInterceptor(logging)
        .callTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val oAuthApi: OAuthApi by lazy {
        createRetrofit("https://oauth2.googleapis.com/").create(OAuthApi::class.java)
    }

    val userApi: UserApi by lazy {
        createRetrofit("https://www.googleapis.com/oauth2/v2/").create(UserApi::class.java)
    }
}


/**
 * this extension function attempts to parse error from failed google oauth request
 * and return error description
 * used in [ProfileRepo] and [TokenRepo]
 */
fun Exception.humanReadableError(): String {
    return when (this) {
        is HttpException -> {
            return try {
                val gson = Gson()
                val responseBody = response()?.errorBody()?.string()
                val errorResponse = gson.fromJson(responseBody, GoogleErrorResponse::class.java)
                errorResponse.errorDescription
            } catch (e: Exception) {
                toString()
            }
        }

        is UnknownHostException -> "Not Internet connection!"
        else -> this.toString()
    }


}