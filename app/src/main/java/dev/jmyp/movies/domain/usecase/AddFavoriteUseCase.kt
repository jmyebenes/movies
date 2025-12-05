package dev.jmyp.movies.domain.usecase

import dev.jmyp.movies.data.repository.FavoriteRepository

class AddFavoriteUseCase(private val favoriteRepository: FavoriteRepository) {
    suspend operator fun invoke(movieId: Int) {
        favoriteRepository.addFavorite(movieId)
    }
}