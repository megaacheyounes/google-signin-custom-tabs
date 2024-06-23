package com.younes.oauth_signing_tabs_demo.data.repo

import com.younes.oauth_signing_tabs_demo.data.entities.GoogleTokenResponse
import com.younes.oauth_signing_tabs_demo.data.network.RetrofitClient
import com.younes.oauth_signing_tabs_demo.data.network.humanReadableError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.UnknownHostException

class TokenRepo {
    suspend fun getAccessToken(code: String): Flow<Result<GoogleTokenResponse>> = flow {
        try {
            val clientId = com.younes.oauth_signing_tabs_demo.BuildConfig.CLIENT_ID
            val clientSecret = com.younes.oauth_signing_tabs_demo.BuildConfig.CLIENT_SECRET
            val redirectUri = com.younes.oauth_signing_tabs_demo.BuildConfig.REDIRECT_URI
            val accessToken = RetrofitClient.oAuthApi.exchangeCodeForToken(
                code,
                clientId,
                clientSecret,
                redirectUri
            )
            emit(Result.success(accessToken))
        } catch (e: UnknownHostException) {
            emit(Result.failure(Throwable("No internet connection!")))
        } catch (e: HttpException) {
            emit(Result.failure(Throwable(e.humanReadableError())))
        }
    }
}