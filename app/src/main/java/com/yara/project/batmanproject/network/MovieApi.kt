package com.yara.project.batmanproject.network

import com.yara.project.batmanproject.model.Movie
import com.yara.project.batmanproject.model.MoviesResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface MovieApi {
    @GET("?apikey=3e974fca&s=batman")
    fun getBatmanMovies(@Query("page") page: String): Flowable<MoviesResponse>

    @GET("?apikey=3e974fca")
    fun getBatmanMovieDetail(@Query("i") i: String): Flowable<Movie>
}