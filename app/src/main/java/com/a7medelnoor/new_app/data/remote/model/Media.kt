package com.a7medelnoor.new_app.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Media(
    @SerializedName("media-metadata") val MediaMetadata:  List<MediaMetadata>,
)