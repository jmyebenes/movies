package dev.jmyp.movies.ui.screens.personDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import dev.jmyp.movies.ui.base.BaseScreen
import dev.jmyp.movies.ui.components.BiographyView
import dev.jmyp.movies.ui.components.PersonCreditsView
import dev.jmyp.movies.ui.components.TopBarView
import dev.jmyp.movies.ui.navigation.Route
import dev.jmyp.movies.utils.Constants
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PersonDetailScreen(
    viewModel: PersonDetailViewModel = koinViewModel(parameters = { parametersOf(args.id) }),
    navController: NavController,
    args: Route.PersonDetail
) {
    val state by viewModel.state.collectAsState()
    var snackBarMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is PersonDetailViewModel.PersonDetailUiEvent.NavigateTo -> navController.navigate(
                    event.route
                )

                is PersonDetailViewModel.PersonDetailUiEvent.ShowSnackBar -> snackBarMessage =
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
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = "${Constants.Api.BASE_IMG_URL}${state.person?.profilePath}",
                    contentDescription = state.person?.name,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.background.copy(alpha = 0f),
                                    MaterialTheme.colorScheme.background
                                ),
                                radius = 1500f
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                ) {
                    Text(
                        text = state.person?.name.toString(),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Text(
                        text = state.person?.knownForDepartment.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
            BiographyView(state.person?.biography)
            PersonCreditsView(
                credits = state.credits,
                onClick = {
                    viewModel.onEvent(PersonDetailViewModel.PersonDetailEvent.OnMovieClick(it))
                },
                onSeeAllClick = if (state.hasMoreCredits) {
                    { viewModel.onEvent(PersonDetailViewModel.PersonDetailEvent.OnSeeAllClick) }
                } else null
            )
        }
    }
}