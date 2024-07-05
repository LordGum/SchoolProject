package com.example.schoolproject.presentation.login

import android.content.Context
import android.util.Log
import com.example.schoolproject.data.network.TokenPreferences
import com.yandex.authsdk.YandexAuthException
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthToken

fun handleResult(result: YandexAuthResult, context: Context) {
    when (result) {
        is YandexAuthResult.Success -> onSuccessAuth(result.token, context )
        is YandexAuthResult.Failure -> onProcessError(result.exception)
        YandexAuthResult.Cancelled -> onCancelled()
    }
}

private fun onSuccessAuth(token: YandexAuthToken, context: Context) {
    TokenPreferences(context).saveToken(token)
}

private fun onProcessError(exception: YandexAuthException) {
    Log.d("tag", "exception $exception")
}

private fun onCancelled() {
    Log.d("tag", "cancel")
}