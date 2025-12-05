package dev.jmyp.movies.domain.usecase

import dev.jmyp.movies.data.repository.MoviesRepository


class MovieDetailUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(id: Int) = repository.getMovieDetail(id)
}