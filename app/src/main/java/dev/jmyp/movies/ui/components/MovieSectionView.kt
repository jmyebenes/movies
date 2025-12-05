package dev.jmyp.movies.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import dev.jmyp.movies.data.model.Movie

@Composable
fun MovieSectionView(
    title: String,
    movies: List<Movie>?,
    movieSectionType: MovieSectionType,
    onMovieClick: (Int) -> Unit,
    onSeeAllClick: (() -> Unit)? = null
) {
    if (movies.isNullOrEmpty()) return
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    TitleView(title = title, onClick = onSeeAllClick)
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(movies.size) {
            MovieView(
                movie = movies[it],
                modifier = Modifier
                    .width(screenWidth * movieSectionType.itemWidthFraction),
                aspectRatio = movieSectionType.aspectRatio,
                onClick = {
                    onMovieClick(movies[it].id)
                })
        }
    }
}

enum class MovieSectionType(
    val itemWidthFraction: Float,
    val aspectRatio: Float
) {
    BIG(0.6f, 1.6f),
    SMALL(0.3f, 0.7f)
}