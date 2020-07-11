package com.yara.project.batmanproject.database

import android.icu.text.CaseMap
import androidx.lifecycle.LiveData
import androidx.room.*
import com.yara.project.batmanproject.model.Movie
import com.yara.project.batmanproject.model.Response
import io.reactivex.Flowable

@Dao
interface MovieDao {
    @Query("SELECT * FROM tbl_Movie")
    fun getMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM tbl_Movie WHERE imdbID=:imdbId")
    fun getMovieById(imdbId: String): LiveData<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movie: Movie): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMovies(movie: Movie)

    @Query("UPDATE tbl_Movie SET title=:title, poster=:poster, type=:type, year=:year WHERE imdbID = :imdId")
    fun updateMovies(title: String?, type: String?, year: String?, poster: String?, imdId: String?)
}