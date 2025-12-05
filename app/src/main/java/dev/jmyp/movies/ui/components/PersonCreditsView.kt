package dev.jmyp.movies.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.jmyp.movies.R
import dev.jmyp.movies.data.model.Movie

@Composable
fun PersonCreditsView(
    credits: List<Movie>?,
    onClick: (Int) -> Unit,
    onSeeAllClick: (() -> Unit)? = null
) {
    if (credits.isNullOrEmpty()) return
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    TitleView(title = stringResource(R.string.person_detail_title_movies), onClick = onSeeAllClick)
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(credits.size) {
            MovieView(
                movie = credits[it],
                modifier = Modifier
                    .width(screenWidth * 0.3f),
                aspectRatio = 0.7f,
                onClick = {
                    onClick(credits[it].id)
                })
        }
    }
}