package dev.jmyp.movies.domain.usecase

import dev.jmyp.movies.data.repository.FavoriteRepository

class RemoveFavoriteUseCase(private val favoriteRepository: FavoriteRepository) {
    suspend operator fun invoke(movieId: Int) {
        favoriteRepository.removeFavorite(movieId)
    }
}