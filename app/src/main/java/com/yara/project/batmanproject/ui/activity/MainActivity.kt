package com.yara.project.batmanproject.ui.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yara.project.batmanproject.R
import com.yara.project.batmanproject.model.Movie
import com.yara.project.batmanproject.ui.MovieViewModel
import com.yara.project.batmanproject.ui.adapter.MoviesAdapter
import com.yara.project.batmanproject.viewmodel.ViewModelProvidersFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    lateinit var movieViewModel: MovieViewModel

    lateinit var layoutManager: LinearLayoutManager
    var page = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movieViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(MovieViewModel::class.java)

        layoutManager = LinearLayoutManager(applicationContext)
        rvMovies.layoutManager = layoutManager
        rvMovies.adapter = moviesAdapter

        movieViewModel.getBatmanMovies(page.toString())
        observe()

        // pagination get movies
        rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastVisibleItemPosition() == moviesAdapter.itemCount -1){
                    page++
                    movieViewModel.getBatmanMovies(page.toString())
                }
            }
        })
    }

    // observe movies
    private fun observe() {
        movieViewModel.observeMovies().observe(this, Observer {
            setRecyclerView(it.data)
        })
    }

    private fun setRecyclerView(movies: List<Movie>?){
        if (movies != null && movies.isNotEmpty()) {
            moviesAdapter.addMovies(movies)
            moviesAdapter.onClickItemListener = { movie, view ->
                val intent = Intent(applicationContext, MovieDetailActivity::class.java)
                intent.putExtra("movie", movie)
                var activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this, Pair(view, "imvMovie")
                )
                startActivity(intent, activityOptions.toBundle())
            }
        }
    }
}