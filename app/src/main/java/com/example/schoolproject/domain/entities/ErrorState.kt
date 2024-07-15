package com.example.schoolproject.domain.entities

import com.example.schoolproject.R

sealed class ErrorState {

    data object NoInternetError : ErrorState() {
        override fun toStringResource(): Int = R.string.no_internet_error
    }

    data object RequestError : ErrorState() {
        override fun toStringResource(): Int = R.string.request_error
    }

    data object ServerError : ErrorState() {
        override fun toStringResource(): Int = R.string.server_error
    }

    data object NoAuthError : ErrorState() {
        override fun toStringResource(): Int = R.string.no_auth_error
    }

    data object NotFoundError : ErrorState() {
        override fun toStringResource(): Int = R.string.no_found_error
    }

    data object UnknownError : ErrorState() {
        override fun toStringResource(): Int = R.string.unknown_error
    }

    abstract fun toStringResource(): Int
}