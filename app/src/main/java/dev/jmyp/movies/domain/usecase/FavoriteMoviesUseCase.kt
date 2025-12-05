package dev.jmyp.movies.domain.usecase

import dev.jmyp.movies.data.repository.MoviesRepository

class FavoriteMoviesUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(ids: List<Int>) = repository.getFavoriteMovies(ids)
}