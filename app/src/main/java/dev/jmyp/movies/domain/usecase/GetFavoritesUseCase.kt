package dev.jmyp.movies.domain.usecase

import dev.jmyp.movies.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.first

class GetFavoritesUseCase(private val favoriteRepository: FavoriteRepository) {
    suspend operator fun invoke(): List<Int> {
        val set = favoriteRepository.getFavoriteIds().first()
        return set.mapNotNull { it.toIntOrNull() }
    }
}