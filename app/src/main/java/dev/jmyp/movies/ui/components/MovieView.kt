package dev.jmyp.movies.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.jmyp.movies.data.model.Movie
import dev.jmyp.movies.utils.Constants

@Composable
fun MovieView(
    movie: Movie,
    modifier: Modifier = Modifier,
    aspectRatio: Float = 1f,
    onClick: () -> Unit = {}
) {

    Column(modifier = modifier) {
        Card(
            modifier = modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
                .aspectRatio(aspectRatio)
                .shadow(8.dp, RoundedCornerShape(8.dp))
                .clickable { onClick() }
        ) {
            AsyncImage(
                model = "${Constants.Api.BASE_IMG_URL}${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                placeholder = placeholderView(
                    MaterialTheme.colorScheme.outline,
                    Icons.Default.Videocam,
                    MaterialTheme.colorScheme.secondary
                ),
                error = placeholderView(
                    MaterialTheme.colorScheme.outline,
                    Icons.Default.Videocam,
                    MaterialTheme.colorScheme.secondary
                )
            )
        }
        Text(
            text = movie.title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            maxLines = 1,
            overflow = Ellipsis
        )
    }
}