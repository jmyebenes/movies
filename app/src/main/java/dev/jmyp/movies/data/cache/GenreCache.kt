package dev.jmyp.movies.data.cache

import dev.jmyp.movies.data.model.Genre

class GenreCache {
    private val genres = mutableMapOf<Int, String>()

    fun setGenres(list: List<Genre>) {
        genres.clear()
        list.forEach { genres[it.id] = it.name }
    }

    fun getNameForId(id: Int): String? = genres[id]

    fun mapIdsToNames(ids: List<Int>): List<String> =
        ids.mapNotNull { genres[it] }
}