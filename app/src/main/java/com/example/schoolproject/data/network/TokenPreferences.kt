package com.example.schoolproject.data.network

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.yandex.authsdk.YandexAuthToken
import java.util.Date
import javax.inject.Inject

class TokenPreferences @Inject constructor(context: Context) {

    private val masterKeyAlias = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "my_secure_prefs",
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveToken(token: YandexAuthToken) {
        val editor = sharedPreferences.edit()
        editor.putString("tokenValue", token.value)
        editor.putLong("tokenExpireDate", Date().time + token.expiresIn - HOUR_IN_MILLIS)
        editor.apply()
    }

    fun getToken(): YandexAuthToken? {
        val value = sharedPreferences.getString("tokenValue", null)
        val expiresIn = sharedPreferences.getLong("tokenExpireDate", 0)
        val token = if (value == null) return null else YandexAuthToken(value, expiresIn)
        return token
    }

    companion object {
        private const val HOUR_IN_MILLIS = 3_600_000
    }
}