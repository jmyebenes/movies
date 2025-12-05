package dev.jmyp.movies.data.mapper

import dev.jmyp.movies.data.model.Movie
import dev.jmyp.movies.data.model.MovieDetail

fun MovieDetail.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        genreIds = this.genres.map { it.id },
        genreNames = this.genres.map { it.name },
        voteAverage = this.voteAverage,
        releaseDate = this.releaseDate,
        backdropPath = this.backdropPath
    )
}