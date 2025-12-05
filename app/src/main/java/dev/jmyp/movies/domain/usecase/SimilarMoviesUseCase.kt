package dev.jmyp.movies.domain.usecase

import dev.jmyp.movies.data.repository.MoviesRepository


class SimilarMoviesUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(id: Int) = repository.getSimilarMovies(id)
}