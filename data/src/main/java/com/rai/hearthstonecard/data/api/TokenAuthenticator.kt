package com.rai.hearthstonecard.data.api

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named
import javax.inject.Inject


internal class TokenAuthenticator @Inject constructor(private val tokenPreferences: TokenPrefs) : Authenticator, KoinComponent {

    override fun authenticate(route: Route?, response: Response): Request? {

        if (responseCount(response) >= 2) {
            return null
        }

        val tokenResponse = get<String>(named("accessToken"))
        tokenPreferences.saveAuthToken(
            tokenResponse
        )
        return response.request().newBuilder()
            .header("Authorization", "Bearer ${tokenPreferences.fetchAuthToken()}")
            .build()
    }


    private fun responseCount(response: Response): Int {
        var count = 1
        var res = response.priorResponse()
        while (res != null) {
            count++
            res = res.priorResponse()
        }
        return count
    }


}