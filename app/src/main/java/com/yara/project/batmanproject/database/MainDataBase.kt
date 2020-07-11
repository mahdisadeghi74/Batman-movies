package com.yara.project.batmanproject.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yara.project.batmanproject.model.Movie
import com.yara.project.batmanproject.model.RatingConverter

@Database(entities = [Movie::class], version = 4, exportSchema = false)
@TypeConverters(RatingConverter::class)
abstract class MainDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}