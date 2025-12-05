package dev.jmyp.movies.ui.screens.movieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jmyp.movies.data.model.Cast
import dev.jmyp.movies.data.model.Crew
import dev.jmyp.movies.data.model.Movie
import dev.jmyp.movies.data.model.MovieDetail
import dev.jmyp.movies.data.model.ReviewResult
import dev.jmyp.movies.data.network.Resource
import dev.jmyp.movies.domain.usecase.AddFavoriteUseCase
import dev.jmyp.movies.domain.usecase.CreditsUseCase
import dev.jmyp.movies.domain.usecase.IsFavoriteUseCase
import dev.jmyp.movies.domain.usecase.MovieDetailUseCase
import dev.jmyp.movies.domain.usecase.RemoveFavoriteUseCase
import dev.jmyp.movies.domain.usecase.SimilarMoviesUseCase
import dev.jmyp.movies.ui.navigation.MovieCategory
import dev.jmyp.movies.ui.navigation.PersonCategory
import dev.jmyp.movies.ui.navigation.Route
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieId: Int,
    private val movieDetailUseCase: MovieDetailUseCase,
    private val creditsUseCase: CreditsUseCase,
    private val similarMoviesUseCase: SimilarMoviesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase
) : ViewModel() {

    data class MovieDetailState(
        val movie: MovieDetail? = null,
        val cast: List<Cast>? = null,
        val crew: List<Crew>? = null,
        val reviews: List<ReviewResult>? = null,
        val similarMovies: List<Movie>? = null,
        val hasMoreCast: Boolean = false,
        val hasMoreCrew: Boolean = false,
        val hasMoreSimilarMovies: Boolean = false,
        val isFavorite: Boolean = false,
        val isLoading: Boolean = false
    )

    sealed class MovieDetailEvent {
        data class OnMovieClick(val id: Int) : MovieDetailEvent()
        data class OnPersonClick(val id: Int) : MovieDetailEvent()
        data class OnSeeAllCreditsClick(val id: Int, val personCategory: PersonCategory) :
            MovieDetailEvent()

        data class OnSeeAllSimilarClick(val id: Int) : MovieDetailEvent()
        data class OnFavoriteClick(val isFavorite: Boolean) : MovieDetailEvent()
    }

    sealed class MovieDetailUiEvent {
        data class NavigateTo(val route: Route) : MovieDetailUiEvent()
        data class ShowSnackBar(val message: String) : MovieDetailUiEvent()
    }

    private val _state = MutableStateFlow(MovieDetailState())
    val state: StateFlow<MovieDetailState> = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<MovieDetailUiEvent>()
    val uiEvent: SharedFlow<MovieDetailUiEvent> = _uiEvent.asSharedFlow()

    init {
        getMovieDetail()
        getCredits()
        getSimilarMovies()
        isFavorite()
    }

    fun onEvent(event: MovieDetailEvent) {
        when (event) {
            is MovieDetailEvent.OnMovieClick -> navigate(Route.MovieDetail(event.id))
            is MovieDetailEvent.OnPersonClick -> navigate(Route.PersonDetail(event.id))
            is MovieDetailEvent.OnSeeAllCreditsClick -> navigate(
                Route.AllCredits(
                    event.id,
                    event.personCategory
                )
            )

            is MovieDetailEvent.OnSeeAllSimilarClick -> navigate(
                Route.AllMovies(
                    event.id,
                    MovieCategory.SIMILAR
                )
            )

            is MovieDetailEvent.OnFavoriteClick -> toggleFavorite(event.isFavorite)
        }
    }

    private fun getMovieDetail() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            when (val result = movieDetailUseCase(movieId)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        movie = result.data,
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _uiEvent.emit(MovieDetailUiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

    private fun getCredits() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            when (val result = creditsUseCase(movieId)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        cast = result.data.cast.take(10),
                        crew = result.data.crew.take(10),
                        hasMoreCast = result.data.cast.size > 10,
                        hasMoreCrew = result.data.crew.size > 10,
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _uiEvent.emit(MovieDetailUiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

    private fun getSimilarMovies() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            when (val result = similarMoviesUseCase(movieId)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        similarMovies = result.data.results.take(10),
                        hasMoreSimilarMovies = result.data.results.size > 10,
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _uiEvent.emit(MovieDetailUiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

    private fun isFavorite() {
        viewModelScope.launch {
            val favorite = isFavoriteUseCase(movieId)
            _state.value = _state.value.copy(isFavorite = favorite)
        }
    }

    private fun toggleFavorite(isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                addFavoriteUseCase(movieId)
            } else {
                removeFavoriteUseCase(movieId)
            }
            _state.value = _state.value.copy(isFavorite = isFavorite)
        }
    }

    private fun navigate(route: Route) {
        viewModelScope.launch {
            _uiEvent.emit(MovieDetailUiEvent.NavigateTo(route))
        }
    }
}