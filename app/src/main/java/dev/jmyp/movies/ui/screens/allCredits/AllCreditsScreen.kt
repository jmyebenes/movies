package dev.jmyp.movies.ui.screens.allCredits

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import dev.jmyp.movies.ui.base.BaseScreen
import dev.jmyp.movies.ui.components.AllCreditsView
import dev.jmyp.movies.ui.components.TopBarView
import dev.jmyp.movies.ui.navigation.Route
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AllCreditsScreen(
    viewModel: AllCreditsViewModel = koinViewModel(parameters = {
        parametersOf(
            args.personId,
            args.personCategory
        )
    }),
    navController: NavController,
    args: Route.AllCredits
) {

    val state by viewModel.state.collectAsState()
    var snackBarMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is AllCreditsViewModel.AllCreditsUiEvent.NavigateTo -> navController.navigate(event.route)
                is AllCreditsViewModel.AllCreditsUiEvent.ShowSnackBar -> snackBarMessage =
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
        AllCreditsView(state.allCredits) {
            viewModel.onEvent(AllCreditsViewModel.AllCreditsEvent.OnPersonClick(it))
        }
    }

}