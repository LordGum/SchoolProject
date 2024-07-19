package com.example.schoolproject.presentation.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView
import com.example.schoolproject.ApplicationClass
import com.example.schoolproject.ViewModelFactory
import com.example.schoolproject.presentation.divkit.AssetReader
import com.example.schoolproject.presentation.divkit.Div2ViewFactory
import com.example.schoolproject.ui.theme.SchoolProjectTheme
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.glide.GlideDivImageLoader
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    private val assetsReader = AssetReader(this)

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

            val imageLoader = GlideDivImageLoader(this)
            val configuration = DivConfiguration.Builder(imageLoader).build()

            val divJson = assetsReader.read("sample.json")
            val templatesJson = divJson.optJSONObject("templates")
            val cardJson = divJson.getJSONObject("card")

            val divContext = Div2Context(
                baseContext = this,
                configuration = configuration,
                lifecycleOwner = this
            )
            val divView = Div2ViewFactory(divContext, templatesJson).createView(cardJson)
            AndroidView(factory = { context -> divView })

            val currentTheme = isSystemInDarkTheme()
            val isDark = remember { mutableStateOf( currentTheme ) }
            SchoolProjectTheme(darkTheme = isDark.value) {

//                BaseScreen(
//                    viewModelFactory,
//                    isDark = isDark.value,
//                    changeTheme = { isDark.value = it }
//                )


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


