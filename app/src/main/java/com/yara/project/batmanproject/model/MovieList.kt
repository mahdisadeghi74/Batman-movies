package com.yara.project.batmanproject.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tbl_Movie")
@Parcelize
class MovieList(
    @SerializedName("Title")
    @Expose
    var title:String ?= "",
    @SerializedName("Year")
    @Expose
    var year:String?= "",

    @SerializedName("imdbID")
    @Expose
    @PrimaryKey
    var imdbID:String= "",
    @SerializedName("Type")
    @Expose
    var type:String?= "",

    @SerializedName("Poster")
    @Expose
    var poster:String?= ""
) : Parcelable{
}