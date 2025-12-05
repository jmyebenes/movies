package dev.jmyp.movies.domain.usecase

import dev.jmyp.movies.data.repository.GenreRepository


class GenreUseCase(private val repository: GenreRepository) {
    suspend operator fun invoke() = repository.getGenres()
}