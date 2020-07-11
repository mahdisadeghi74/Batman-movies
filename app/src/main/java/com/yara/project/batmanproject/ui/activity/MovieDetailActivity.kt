package com.yara.project.batmanproject.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import com.yara.project.batmanproject.R
import com.yara.project.batmanproject.model.Movie
import com.yara.project.batmanproject.model.Response
import com.yara.project.batmanproject.ui.MovieViewModel
import com.yara.project.batmanproject.viewmodel.ViewModelProvidersFactory
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_movie_detail.*
import javax.inject.Inject

class MovieDetailActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    lateinit var movieViewModel: MovieViewModel
    var movie: Movie? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        setSupportActionBar(tlbDetail)

        movieViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(MovieViewModel::class.java)
        movie = intent.getParcelableExtra("movie")
        movieViewModel.getBatmanMovieDetail(movie?.imdbID ?: "")
        setImage(movie)

        supportActionBar?.title = movie?.title
        movieViewModel.observeMovieDetail().observe(this, Observer {response ->
            if (response.status == Response.Status.SUCCESS) {
                var movie = response.data
                tvMovieDetail.text = "${movie?.rated} | ${movie?.runtime} | ${movie?.genre} | ${movie?.type}(${movie?.year})"
                tvContent.text = movie?.plot
                tvDirectorNames.text = movie?.director
                tvWriterNames.text = movie?.writer
                tvActorsNames.text = movie?.actors
                tvAwardsNames.text = movie?.awards
                if (movie?.ratings?.size ?: 0 > 0)
                    tvImdbScore.text = movie?.ratings?.get(0)?.value ?: "--"
                if (movie?.ratings?.size ?: 0 > 1)
                tvRottenTomatoScore.text = movie?.ratings?.get(1)?.value ?: "--"
                if (movie?.ratings?.size ?: 0 > 2)
                    tvMetacriticScore.text = movie?.ratings?.get(2)?.value ?: "--"
            }
            setImage(movie)
        })
    }

    private fun setImage(movie: Movie?) {
        Picasso.get()
            .load(movie?.poster)
            .into(imvMovieDetail)
        Picasso.get()
            .load(movie?.poster)
            .into(imvBackground)
    }
}