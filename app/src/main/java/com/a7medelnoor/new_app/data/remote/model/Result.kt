package com.a7medelnoor.new_app.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Result(
    val byline: String,
    val id: Long,
    val media:  @RawValue List<Media>,
    val published_date: String,
    val section: String,
    val source: String,
    val subsection: String,
    val title: String,
    val updated: String,
) : Parcelable