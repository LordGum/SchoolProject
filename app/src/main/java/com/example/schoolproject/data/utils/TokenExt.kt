package com.example.schoolproject.data.utils

import com.yandex.authsdk.YandexAuthToken

fun YandexAuthToken.isValid(): Boolean {
    val expireDate = this.expiresIn
    return expireDate > System.currentTimeMillis()
}