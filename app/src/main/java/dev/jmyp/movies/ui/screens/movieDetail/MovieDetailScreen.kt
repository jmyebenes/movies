package dev.jmyp.movies.ui.screens.movieDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import dev.jmyp.movies.R
import dev.jmyp.movies.ui.base.BaseScreen
import dev.jmyp.movies.ui.components.CreditsView
import dev.jmyp.movies.ui.components.GenresView
import dev.jmyp.movies.ui.components.MovieInfoView
import dev.jmyp.movies.ui.components.MovieSectionType
import dev.jmyp.movies.ui.components.MovieSectionView
import dev.jmyp.movies.ui.components.SynopsisView
import dev.jmyp.movies.ui.components.TopBarView
import dev.jmyp.movies.ui.navigation.Route
import dev.jmyp.movies.utils.Constants
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = koinViewModel(parameters = { parametersOf(args.id) }),
    navController: NavController,
    args: Route.MovieDetail
) {
    val state by viewModel.state.collectAsState()
    var snackBarMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is MovieDetailViewModel.MovieDetailUiEvent.NavigateTo -> navController.navigate(
                    event.route
                )

                is MovieDetailViewModel.MovieDetailUiEvent.ShowSnackBar -> snackBarMessage =
                    event.message
            }
        }
    }

    BaseScreen(
        topBar = {
            TopBarView(
                onBack = { navController.popBackStack() },
                isFavorite = state.isFavorite,
                onFavoriteClick = {
                    viewModel.onEvent(
                        MovieDetailViewModel.MovieDetailEvent.OnFavoriteClick(
                            !state.isFavorite
                        )
                    )
                })
        },
        snackBarMessage = snackBarMessage,
        onSnackBarDismissed = { snackBarMessage = null },
        isLoading = state.isLoading
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = "${Constants.Api.BASE_IMG_URL}${state.movie?.backdropPath}",
                    contentDescription = state.movie?.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.8f),
                    contentScale = ContentScale.Crop
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
                AsyncImage(
                    model = "${Constants.Api.BASE_IMG_URL}${state.movie?.posterPath}",
                    contentDescription = state.movie?.title,
                    modifier = Modifier
                        .offset(y = (128).dp)
                        .align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(128.dp))
            Text(
                text = state.movie?.title.toString(),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            GenresView(state.movie?.genres)
            MovieInfoView(state.movie)
            Text(
                text = state.movie?.tagline.toString(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            SynopsisView(state.movie?.overview)
            CreditsView(
                cast = state.cast,
                crew = state.crew,
                onPersonClick = {
                    viewModel.onEvent(MovieDetailViewModel.MovieDetailEvent.OnPersonClick(it))
                },
                onSeeAllCastClick = if (state.hasMoreCast) {
                    {
                        viewModel.onEvent(
                            MovieDetailViewModel.MovieDetailEvent.OnSeeAllCreditsClick(
                                args.id,
                                it
                            )
                        )
                    }
                } else null,
                onSeeAllCrewClick = if (state.hasMoreCrew) {
                    {
                        viewModel.onEvent(
                            MovieDetailViewModel.MovieDetailEvent.OnSeeAllCreditsClick(
                                args.id,
                                it
                            )
                        )
                    }
                } else null
            )

            MovieSectionView(
                title = stringResource(R.string.movie_detail_title_similar),
                movieSectionType = MovieSectionType.SMALL,
                movies = state.similarMovies,
                onMovieClick = {
                    viewModel.onEvent(MovieDetailViewModel.MovieDetailEvent.OnMovieClick(it))
                },
                onSeeAllClick = if (state.hasMoreSimilarMovies) {
                    {
                        viewModel.onEvent(
                            MovieDetailViewModel.MovieDetailEvent.OnSeeAllSimilarClick(
                                args.id
                            )
                        )
                    }
                } else null)
        }
    }
}