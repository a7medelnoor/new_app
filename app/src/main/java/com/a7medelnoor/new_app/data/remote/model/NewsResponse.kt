package com.a7medelnoor.new_app.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsResponse(
    val results: List<Result>,
):Parcelable