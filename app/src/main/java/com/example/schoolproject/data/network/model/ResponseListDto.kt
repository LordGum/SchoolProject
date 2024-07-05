package com.example.schoolproject.data.network.model

import com.google.gson.annotations.SerializedName

data class ResponseListDto (
    @SerializedName("status") val status: String,
    @SerializedName("list") val list: List<ElementDto>,
    @SerializedName("revision") val revision: Int
)