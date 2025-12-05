package dev.jmyp.movies.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import dev.jmyp.movies.R
import dev.jmyp.movies.ui.base.BaseScreen
import dev.jmyp.movies.ui.components.MovieSectionType
import dev.jmyp.movies.ui.components.MovieSectionView
import dev.jmyp.movies.ui.components.TrendingMoviesView
import dev.jmyp.movies.ui.navigation.MovieCategory
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel(), navController: NavController) {

    val state by viewModel.state.collectAsState()
    var snackBarMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is HomeViewModel.HomeUiEvent.NavigateTo -> navController.navigate(event.route)
                is HomeViewModel.HomeUiEvent.ShowSnackBar -> snackBarMessage = event.message
            }
        }
    }

    LaunchedEffect(navController.currentBackStackEntry) {
        viewModel.onEvent(HomeViewModel.HomeEvent.RefreshFavorites)
    }

    BaseScreen(
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
            TrendingMoviesView(state.trendingMovies) {
                viewModel.onEvent(HomeViewModel.HomeEvent.OnMovieClick(it))
            }
            MovieSectionView(
                title = stringResource(R.string.home_title_popular),
                movies = state.popularMovies,
                movieSectionType = MovieSectionType.BIG,
                onMovieClick = {
                    viewModel.onEvent(HomeViewModel.HomeEvent.OnMovieClick(it))
                },
                onSeeAllClick = if (state.hasMorePopularMovies) {
                    {
                        viewModel.onEvent(HomeViewModel.HomeEvent.OnSeeAllClick(MovieCategory.POPULAR))
                    }
                } else null)
            MovieSectionView(
                title = stringResource(R.string.home_title_now_playing),
                movies = state.nowPlayingMovies,
                movieSectionType = MovieSectionType.SMALL,
                onMovieClick = {
                    viewModel.onEvent(HomeViewModel.HomeEvent.OnMovieClick(it))
                },
                onSeeAllClick = if (state.hasMoreNowPlayingMovies) {
                    {
                        viewModel.onEvent(HomeViewModel.HomeEvent.OnSeeAllClick(MovieCategory.NOW_PLAYING))
                    }
                } else null)
            MovieSectionView(
                title = stringResource(R.string.home_title_upcoming),
                movies = state.upcomingMovies,
                movieSectionType = MovieSectionType.SMALL,
                onMovieClick = {
                    viewModel.onEvent(HomeViewModel.HomeEvent.OnMovieClick(it))
                },
                onSeeAllClick = if (state.hasMoreUpcomingMovies) {
                    {
                        viewModel.onEvent(HomeViewModel.HomeEvent.OnSeeAllClick(MovieCategory.UPCOMING))
                    }
                } else null)
            MovieSectionView(
                title = stringResource(R.string.home_title_favorites),
                movies = state.favoriteMovies,
                movieSectionType = MovieSectionType.SMALL,
                onMovieClick = {
                    viewModel.onEvent(HomeViewModel.HomeEvent.OnMovieClick(it))
                },
                onSeeAllClick = if (state.hasMoreFavoriteMovies) {
                    {
                        viewModel.onEvent(HomeViewModel.HomeEvent.OnSeeAllClick(MovieCategory.UPCOMING))
                    }
                } else null)
        }
    }

}