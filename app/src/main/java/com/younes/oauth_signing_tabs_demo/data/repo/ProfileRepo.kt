package com.younes.oauth_signing_tabs_demo.data.repo

import com.younes.oauth_signing_tabs_demo.data.network.RetrofitClient
import com.younes.oauth_signing_tabs_demo.data.network.humanReadableError
import com.younes.oauth_signing_tabs_demo.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.UnknownHostException

class ProfileRepo {
    suspend fun getUserProfile(accessToken: String): Flow<Result<UserProfile>> = flow {
        try {
            val googleProfile = RetrofitClient.userApi.fetchUserProfile("Bearer $accessToken")
            val userProfile = UserProfile(
                name = googleProfile.given_name,
                email = googleProfile.email,
                avatarUrl = googleProfile.picture
            )
            emit(Result.success(userProfile))
        } catch (e: UnknownHostException) {
            emit(Result.failure(Throwable("No internet connection!")))
        } catch (e: HttpException) {
            emit(Result.failure(Throwable(e.humanReadableError())))
        }
    }
}