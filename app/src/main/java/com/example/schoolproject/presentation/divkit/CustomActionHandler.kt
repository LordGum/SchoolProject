package com.example.schoolproject.presentation.divkit

import android.content.Context
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.example.schoolproject.presentation.login.MainActivity
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction

class CustomDivActionHandler() : DivActionHandler() {
    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver
    ): Boolean {
        val url =
            action.url?.evaluate(resolver) ?: return super.handleAction(action, view, resolver)

        return if (url.scheme == SCHEME_SAMPLE && handleCustomAction(url, view.view.context)) {
            true
        } else {
            super.handleAction(action, view, resolver)
        }
    }

    private fun handleCustomAction(action: Uri, context: Context): Boolean {
        val intent = MainActivity().makeIntent(context)
        return when (action.host) {
            "close" -> {
                startActivity(context, intent, null)
                true
            }

            else -> false
        }
    }

    companion object {
        const val SCHEME_SAMPLE = "my-action"
    }
}

