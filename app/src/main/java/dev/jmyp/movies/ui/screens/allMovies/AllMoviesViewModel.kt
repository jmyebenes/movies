package dev.jmyp.movies.ui.screens.allMovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jmyp.movies.data.model.Movie
import dev.jmyp.movies.data.model.MovieResult
import dev.jmyp.movies.data.model.PersonCreditsResult
import dev.jmyp.movies.data.network.Resource
import dev.jmyp.movies.domain.usecase.NowPlayingUseCase
import dev.jmyp.movies.domain.usecase.PersonCreditsUseCase
import dev.jmyp.movies.domain.usecase.PopularMoviesUseCase
import dev.jmyp.movies.domain.usecase.SimilarMoviesUseCase
import dev.jmyp.movies.domain.usecase.UpcomingUseCase
import dev.jmyp.movies.ui.navigation.MovieCategory
import dev.jmyp.movies.ui.navigation.Route
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllMoviesViewModel(
    private val referenceId: Int? = null,
    private val movieCategory: MovieCategory,
    private val popularMoviesUseCase: PopularMoviesUseCase,
    private val nowPlayingUseCase: NowPlayingUseCase,
    private val upcomingUseCase: UpcomingUseCase,
    private val similarMoviesUseCase: SimilarMoviesUseCase,
    private val personCreditsUseCase: PersonCreditsUseCase
) : ViewModel() {

    data class AllMoviesState(
        val allMovies: List<Movie> = emptyList(),
        val isLoading: Boolean = false
    )

    sealed class AllMoviesEvent {
        data class OnMovieClick(val id: Int) : AllMoviesEvent()
    }

    sealed class AllMoviesUiEvent {
        data class NavigateTo(val route: Route) : AllMoviesUiEvent()
        data class ShowSnackBar(val message: String) : AllMoviesUiEvent()
    }

    private val _state = MutableStateFlow(AllMoviesState())
    val state: StateFlow<AllMoviesState> = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<AllMoviesUiEvent>()
    val uiEvent: SharedFlow<AllMoviesUiEvent> = _uiEvent.asSharedFlow()

    init {
        getAllMovies()
    }

    fun onEvent(event: AllMoviesEvent) {
        when (event) {
            is AllMoviesEvent.OnMovieClick -> navigate(Route.MovieDetail(event.id))
        }
    }

    private fun getAllMovies() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = when (movieCategory) {
                MovieCategory.POPULAR -> popularMoviesUseCase()
                MovieCategory.NOW_PLAYING -> nowPlayingUseCase()
                MovieCategory.UPCOMING -> upcomingUseCase()
                MovieCategory.SIMILAR -> {
                    referenceId?.let { similarMoviesUseCase(it) }
                }

                MovieCategory.PERSON_CREDITS -> {
                    referenceId?.let { personCreditsUseCase(it) }
                }
            }

            when {
                result is Resource.Success && result.data is MovieResult -> {
                    _state.value = _state.value.copy(
                        allMovies = result.data.results,
                        isLoading = false
                    )
                }

                result is Resource.Success && result.data is PersonCreditsResult -> {
                    _state.value = _state.value.copy(
                        allMovies = result.data.cast,
                        isLoading = false
                    )
                }

                result is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _uiEvent.emit(AllMoviesUiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

    private fun navigate(route: Route) {
        viewModelScope.launch {
            _uiEvent.emit(AllMoviesUiEvent.NavigateTo(route))
        }
    }
}