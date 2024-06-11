package com.personal.tmdb.core.presentation.components

import info.movito.themoviedbapi.model.MovieDb
import info.movito.themoviedbapi.model.tv.TvSeries

data class MediaWrapper(
    val tvSeries: List<TvSeries>? = null,
    val movies: List<MovieDb>? = null
)