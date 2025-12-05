package dev.jmyp.movies.domain.usecase

import dev.jmyp.movies.data.repository.PeopleRepository


class PersonDetailUseCase(private val repository: PeopleRepository) {
    suspend operator fun invoke(id: Int) = repository.getPersonDetail(id)
}