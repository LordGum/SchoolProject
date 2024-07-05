package com.example.schoolproject.data.network.model

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName

data class ElementDto (
    @SerializedName("id") val id: String,
    @SerializedName("text") val text: String,
    @SerializedName("importance") val importance: ImportanceDto,
    @SerializedName("deadline") val deadline: Long?,
    @SerializedName("done") val done: Boolean,
    @SerializedName("color") val color: Color?,
    @SerializedName("created_at") val createdAt: Long,
    @SerializedName("change_at") val changeAt: Long,
    @SerializedName("last_updated_by") val lastUpdatedBy: String
)