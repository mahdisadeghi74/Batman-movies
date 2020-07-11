package com.yara.project.batmanproject.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.yara.project.batmanproject.database.MainDataBase
import com.yara.project.batmanproject.model.Movie
import com.yara.project.batmanproject.model.Response
import com.yara.project.batmanproject.model.MoviesResponse
import com.yara.project.batmanproject.network.MovieApi
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MovieViewModel @Inject constructor(private var movieApi: MovieApi, application: Application) :
    ViewModel() {
    private var batmanMovies = MediatorLiveData<Response<List<Movie>>>()
    private var batmanMovieDetail = MediatorLiveData<Response<Movie>>()

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var mainDataBase: MainDataBase

    fun getBatmanMovies(page: String) {
        val source = LiveDataReactiveStreams.fromPublisher<Response<List<Movie>>>(
            movieApi.getBatmanMovies(page)
                .onErrorReturn {
                    Log.d("TAG", "getBatmanMovies: error return: ${it}")
                    MoviesResponse()
                }
                .map(Function<MoviesResponse, Response<List<Movie>>> {
                    Log.d("TAG", "getBatmanMovies:1 ${it.Search?.get(0)?.title}")
                    Response.success(data = it.Search)
                })
                .subscribeOn(Schedulers.io())
        )

        if (!isNetworkAvailable(context = application)) {
            val offlineSource = mainDataBase.movieDao().getMovies()
                // get continue page
            batmanMovies.addSource(offlineSource, Observer {
                Log.d("TAG", "getBatmanMoviesofline: ${it.size}")
                batmanMovies.value = Response.success(data = it)
                batmanMovies.removeSource(source)
            })
        } else {
            batmanMovies.addSource(source, Observer {
                batmanMovies.value = it
                batmanMovies.removeSource(source)
                for (m in it.data ?: arrayListOf()) {
                   if (mainDataBase.movieDao().insertMovies(m) == -1L){
                       mainDataBase.movieDao().updateMovies(
                           title = m.title,
                           imdId = m.imdbID,
                           poster = m.poster,
                           type = m.type,
                           year = m.year
                       )
                   }
                }
            })
        }

    }

    fun observeMovies(): LiveData<Response<List<Movie>>> {
        return batmanMovies
    }

    fun observeMovieDetail(): LiveData<Response<Movie>> {
        return batmanMovieDetail
    }

    fun getBatmanMovieDetail(imdbId: String) {
        val source = LiveDataReactiveStreams.fromPublisher<Response<Movie>>(
            movieApi.getBatmanMovieDetail(imdbId)
            .onErrorReturn {
                Log.d("TAG", "ondeTAil: ${it}")
                Movie().also { it.imdbID = "-1" }
            }
            .map(Function<Movie, Response<Movie>> {
                if (it.imdbID == "-1")
                    Response.error(data = it, message = "Can not fetch data from server")
                else
                    Response.success(data = it)
            })
            .subscribeOn(Schedulers.io())
        )

        if (!isNetworkAvailable(application)){
            val offlineSource = mainDataBase.movieDao().getMovieById(imdbId = imdbId)
            batmanMovieDetail.addSource(offlineSource, Observer {
                batmanMovieDetail.value = Response.success(data = it)
                batmanMovieDetail.removeSource(source)
            })
        }else {
            batmanMovieDetail.addSource(source, Observer {
                batmanMovieDetail.value = it
                batmanMovieDetail.removeSource(source)
                it.data?.let { movie ->  mainDataBase.movieDao().updateMovies(movie = movie)}
            })
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // For 29 api or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
        // For below 29 api
        else {
            if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
                return true
            }
        }
        return false
    }
}