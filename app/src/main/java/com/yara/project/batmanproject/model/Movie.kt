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
open class Movie(
    @SerializedName("Title")
    @Expose
    var title:String ?= "",
    @SerializedName("Year")
    @Expose
    var year:String?= "",
    @SerializedName("Rated")
    @Expose
    var rated:String?= "",
    @SerializedName("Released")
    @Expose
    var released:String?= "",
    @SerializedName("Runtime")
    @Expose
    var runtime:String?= "",
    @SerializedName("Genre")
    @Expose
    var genre:String?= "",
    @SerializedName("Director")
    @Expose
    var director:String?= "",
    @SerializedName("Writer")
    @Expose
    var writer:String?= "",
    @SerializedName("Ratings")
    @Expose
    var ratings:ArrayList<Rating> = arrayListOf(),
    @SerializedName("Actors")
    @Expose
    var actors:String?= "",
    @SerializedName("Plot")
    @Expose
    var plot:String?= "",
    @SerializedName("Language")
    @Expose
    var language:String?= "",
    @SerializedName("Country")
    @Expose
    var country:String?= "",
    @SerializedName("Awards")
    @Expose
    var awards:String?= "",
    @SerializedName("Poster")
    @Expose
    var poster:String?= "",
    @SerializedName("metascore")
    @Expose
    var metascore:String?= "",
    @SerializedName("imdbRating")
    @Expose
    var imdbRating:String?= "",
    @SerializedName("imdbVotes")
    @Expose
    var imdbVotes:String?= "",
    @SerializedName("imdbID")
    @Expose
    @PrimaryKey
    var imdbID:String= "",
    @SerializedName("Type")
    @Expose
    var type:String?= "",
    @SerializedName("DVD")
    @Expose
    var dvd:String?= "",
    @SerializedName("BoxOffice")
    @Expose
    var boxOffice:String?= "",
    @SerializedName("Production")
    @Expose
    var production:String?= "",
    @SerializedName("Website")
    @Expose
    var website:String?= "",
    @SerializedName("Response")
    @Expose
    var response:String?= ""
) : Parcelable{
}