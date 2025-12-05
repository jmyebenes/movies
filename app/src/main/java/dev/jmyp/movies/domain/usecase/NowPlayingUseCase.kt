package dev.jmyp.movies.domain.usecase

import dev.jmyp.movies.data.repository.MoviesRepository


class NowPlayingUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke() = repository.getNowPlayingMovies()
}