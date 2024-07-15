package com.example.schoolproject.presentation.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import com.example.schoolproject.ApplicationClass
import com.example.schoolproject.ViewModelFactory
import com.example.schoolproject.presentation.BaseScreen
import com.example.schoolproject.ui.theme.SchoolProjectTheme
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component = lazy {
        (application as ApplicationClass).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.value.inject(this)

//        val viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
//        val sdk = YandexAuthSdk.create(YandexAuthOptions(this.applicationContext))
//        val launcher = registerForActivityResult(contract = sdk.contract) { result ->
//            handleResult(result, this.applicationContext)
//            viewModel.performAuthResult()
//        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val currentTheme = isSystemInDarkTheme()
            val isDark = remember { mutableStateOf( currentTheme ) }
            SchoolProjectTheme(darkTheme = isDark.value) {


                BaseScreen(
                    viewModelFactory,
                    isDark = isDark.value,
                    changeTheme = { isDark.value = it }
                )
//                val authState = viewModel.authState.collectAsState(AuthState.Initial)
//
//                when (authState.value) {
//                    is AuthState.Authorized -> {
//                        val preferences = TokenPreferences(applicationContext)
//                        val token = preferences.getToken() ?: throw RuntimeException("token is null")
//                        ApiFactory.initialize(token)
//                        BaseScreen(viewModelFactory)
//                    }
//
//                    is AuthState.NotAuthorized -> {
//                        AuthScreen {
//                            val loginOptions = YandexAuthLoginOptions()
//                            launcher.launch(loginOptions)
//                        }
//                    }
//
//                    is AuthState.Initial -> {}
//                }
            }
        }
    }
}


