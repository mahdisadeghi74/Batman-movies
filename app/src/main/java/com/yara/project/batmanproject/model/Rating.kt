package com.yara.project.batmanproject.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Rating(
    @SerializedName("Source")
    @Expose
    var source: String? = "",
    @SerializedName("Value")
    @Expose
    var value: String? =""
): Parcelable{
}