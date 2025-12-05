package dev.jmyp.movies.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import dev.jmyp.movies.data.model.Movie
import dev.jmyp.movies.utils.Constants

@Composable
fun MovieCarouselView(movie: Movie, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        shape = RectangleShape,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = "${Constants.Api.BASE_IMG_URL}${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}