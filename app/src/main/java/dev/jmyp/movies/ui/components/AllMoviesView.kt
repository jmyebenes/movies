package dev.jmyp.movies.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jmyp.movies.data.model.Movie

@Composable
fun AllMoviesView(allMovies: List<Movie>?, onMovieClick: (Int) -> Unit) {
    if (allMovies.isNullOrEmpty()) return

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, 104.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(allMovies.size) { index ->
            MovieView(
                movie = allMovies[index],
                modifier = Modifier.fillMaxWidth(),
                aspectRatio = 0.7f,
                onClick = { onMovieClick(allMovies[index].id) }
            )
        }
    }
}