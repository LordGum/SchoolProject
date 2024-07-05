package com.example.schoolproject.presentation.login

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModelProvider
import com.example.schoolproject.data.NetworkRepositoryImpl
import com.example.schoolproject.data.network.ApiFactory
import com.example.schoolproject.data.network.TokenPreferences
import com.example.schoolproject.domain.entities.AuthState
import com.example.schoolproject.presentation.BaseScreen
import com.example.schoolproject.ui.theme.SchoolProjectTheme
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        val sdk = YandexAuthSdk.create(YandexAuthOptions(this.applicationContext))
        val launcher = registerForActivityResult (contract = sdk.contract) { result ->
            handleResult(result, this.applicationContext)
            viewModel.performAuthResult()
        }

        val repository = NetworkRepositoryImpl(this.applicationContext, TokenPreferences(applicationContext))
        CoroutineScope(Dispatchers.Default).launch {
            val list = repository.getTodoList()
            Log.d("tag", "newList = ${list.size}")
        }


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SchoolProjectTheme {
                val authState = viewModel.authState.collectAsState(AuthState.Initial)

                when (authState.value) {
                    is AuthState.Authorized -> {
                        val preferences = TokenPreferences(applicationContext)
                        val token = preferences.getToken() ?: throw RuntimeException("token is null")
                        ApiFactory.initialize(token)
                        BaseScreen()
                    }
                    is AuthState.NotAuthorized -> {
                        val loginOptions = YandexAuthLoginOptions()
                        launcher.launch(loginOptions)
                    }
                    is AuthState.Initial -> {}
                }
            }
        }
    }
}


