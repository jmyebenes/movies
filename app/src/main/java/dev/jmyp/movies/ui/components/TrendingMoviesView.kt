package dev.jmyp.movies.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import dev.jmyp.movies.R
import dev.jmyp.movies.data.model.Movie
import dev.jmyp.movies.utils.Formatter


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrendingMoviesView(
    trendingMovies: List<Movie>?,
    onClick: (Int) -> Unit
) {
    if (trendingMovies.isNullOrEmpty()) return
    val pagerState = rememberPagerState(pageCount = { trendingMovies.size })

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.65f)
        ) { page ->
            Box(modifier = Modifier.fillMaxWidth()) {
                MovieCarouselView(
                    movie = trendingMovies[page],
                    modifier = Modifier.fillMaxSize(),
                    onClick = { onClick(trendingMovies[page].id) }
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.background.copy(alpha = 0f),
                                    MaterialTheme.colorScheme.background
                                ),
                                startY = 600f
                            )
                        )
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        trendingMovies[page].title,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            Formatter.getYearFromDate(trendingMovies[page].releaseDate),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(vertical = 2.dp, horizontal = 4.dp)
                        )

                        Text(
                            trendingMovies[page].genreNames.take(3).joinToString(" · "),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    }
                    Text(
                        trendingMovies[page].overview,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 8.dp),
                        maxLines = 3,
                        overflow = Ellipsis
                    )
                    PrimaryButton(
                        text = stringResource(R.string.home_action_more_info),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        onClick(trendingMovies[page].id)
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(trendingMovies.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (isSelected) 10.dp else 6.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                            else MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                        )
                )
            }
        }
    }
}