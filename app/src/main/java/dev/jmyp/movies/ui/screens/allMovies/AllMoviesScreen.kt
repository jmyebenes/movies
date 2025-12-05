package dev.jmyp.movies.ui.screens.allMovies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import dev.jmyp.movies.ui.base.BaseScreen
import dev.jmyp.movies.ui.components.AllMoviesView
import dev.jmyp.movies.ui.components.TopBarView
import dev.jmyp.movies.ui.navigation.Route
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AllMoviesScreen(
    viewModel: AllMoviesViewModel = koinViewModel(parameters = {
        parametersOf(
            args.referenceId,
            args.movieCategory
        )
    }),
    navController: NavController,
    args: Route.AllMovies
) {

    val state by viewModel.state.collectAsState()
    var snackBarMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is AllMoviesViewModel.AllMoviesUiEvent.NavigateTo -> navController.navigate(event.route)
                is AllMoviesViewModel.AllMoviesUiEvent.ShowSnackBar -> snackBarMessage =
                    event.message
            }
        }
    }

    BaseScreen(
        topBar = {
            TopBarView(onBack = { navController.popBackStack() })
        },
        snackBarMessage = snackBarMessage,
        onSnackBarDismissed = { snackBarMessage = null },
        isLoading = state.isLoading
    ) {
        AllMoviesView(state.allMovies) {
            viewModel.onEvent(AllMoviesViewModel.AllMoviesEvent.OnMovieClick(it))
        }
    }

}