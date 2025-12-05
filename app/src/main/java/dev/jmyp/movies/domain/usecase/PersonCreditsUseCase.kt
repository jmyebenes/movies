package dev.jmyp.movies.domain.usecase

import dev.jmyp.movies.data.repository.PeopleRepository


class PersonCreditsUseCase(private val repository: PeopleRepository) {
    suspend operator fun invoke(id: Int) = repository.getPersonCredits(id)
}