package com.yara.project.batmanproject.model

import android.R.attr.data
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList


class RatingConverter(){
    @TypeConverter
    fun stringToList(rating: String?): ArrayList<Rating>{
        val listType: Type =
            object : TypeToken<List<Rating>>() {}.type

        return  Gson().fromJson(rating, listType)
    }

    @TypeConverter
    fun listToString(ratings: ArrayList<Rating>): String{
        return Gson().toJson(ratings)
    }
}