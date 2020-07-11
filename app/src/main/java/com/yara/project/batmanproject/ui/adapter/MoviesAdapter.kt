package com.yara.project.batmanproject.ui.adapter

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.yara.project.batmanproject.R
import com.yara.project.batmanproject.model.Movie
import kotlinx.android.synthetic.main.layout_movie_item.view.*


class MoviesAdapter: RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    private var movies = arrayListOf<Movie>()
    var onClickItemListener: ((Movie, View) -> Unit) ? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_movie_item, parent, false))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position], onClickItemListener)
    }

    fun setMovies(movies: List<Movie>?){
        if (movies != null) {
            this.movies.addAll(movies)
            // Log.d(TAG, "setMovies: ${movies[0].imdbID}")
            notifyDataSetChanged()
        }
    }

    fun addMovies(movies: List<Movie>){
        try {
            val oldItemCount = itemCount
            if (this.movies.size == 0)
                this.movies.addAll(movies)
            else {
                var mList = arrayListOf<Movie>()
                // don't add existed movie
                for (m in movies) {
                    var exist = false
                    for (j in this.movies) {
                        if (m.imdbID == j.imdbID) {
                            exist = true
                            break
                        }
                    }
                    if (!exist)
                        mList.add(m)
                }
                this.movies.addAll(mList)
            }
            notifyItemRangeInserted(oldItemCount, this.movies.size)
        }catch (e: Exception){
            Log.d(TAG, "addMovies: $e")
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie, onClickItemListener: ((Movie, View) -> Unit) ?= null){
            Log.d(TAG, "bind: ${movie.title}")
            itemView.tvTitle.text = movie.title
            itemView.tvTypeYear.text = "${movie.type} ${movie.year}"
            itemView.imvMovie.clipToOutline = true

            itemView.setOnClickListener {
                onClickItemListener?.let {
                    it(movie, itemView.imvMovie)
                }
            }

            if (isNetworkAvailable(itemView.context)){
                Picasso.get()
                    .load(movie.poster)
                    .into(itemView.imvMovie)
            } else{
                Picasso.get()
                    .load(movie.poster)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(itemView.imvMovie)
            }
        }
        private fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            // For 29 api or above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
                return when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->    true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->   true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->   true
                    else ->     false
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
}