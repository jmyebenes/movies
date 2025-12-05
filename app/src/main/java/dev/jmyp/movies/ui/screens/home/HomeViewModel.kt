package dev.jmyp.movies.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jmyp.movies.data.cache.GenreCache
import dev.jmyp.movies.data.model.Movie
import dev.jmyp.movies.data.network.Resource
import dev.jmyp.movies.domain.usecase.FavoriteMoviesUseCase
import dev.jmyp.movies.domain.usecase.GenreUseCase
import dev.jmyp.movies.domain.usecase.GetFavoritesUseCase
import dev.jmyp.movies.domain.usecase.NowPlayingUseCase
import dev.jmyp.movies.domain.usecase.PopularMoviesUseCase
import dev.jmyp.movies.domain.usecase.TrendingMoviesUseCase
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

class HomeViewModel(
    private val trendingMoviesUseCase: TrendingMoviesUseCase,
    private val popularMoviesUseCase: PopularMoviesUseCase,
    private val nowPlayingUseCase: NowPlayingUseCase,
    private val upcomingUseCase: UpcomingUseCase,
    private val genreUseCase: GenreUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val favoriteMoviesUseCase: FavoriteMoviesUseCase,
    private val genreCache: GenreCache
) : ViewModel() {

    data class HomeState(
        val trendingMovies: List<Movie> = emptyList(),
        val popularMovies: List<Movie> = emptyList(),
        val nowPlayingMovies: List<Movie> = emptyList(),
        val upcomingMovies: List<Movie> = emptyList(),
        val favoriteMovies: List<Movie> = emptyList(),
        val hasMorePopularMovies: Boolean = false,
        val hasMoreNowPlayingMovies: Boolean = false,
        val hasMoreUpcomingMovies: Boolean = false,
        val hasMoreFavoriteMovies: Boolean = false,
        val isLoading: Boolean = false
    )

    sealed class HomeEvent {
        data class OnMovieClick(val id: Int) : HomeEvent()
        data class OnSeeAllClick(val movieCategory: MovieCategory) : HomeEvent()
        object RefreshFavorites : HomeEvent()
    }

    sealed class HomeUiEvent {
        data class NavigateTo(val route: Route) : HomeUiEvent()
        data class ShowSnackBar(val message: String) : HomeUiEvent()
    }

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HomeUiEvent>()
    val uiEvent: SharedFlow<HomeUiEvent> = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getGenres()
            getTrendingMovies()
            getPopularMovies()
            getNowPlayingMovies()
            getUpcomingMovies()
            getFavorites()
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnMovieClick -> navigate(Route.MovieDetail(event.id))
            is HomeEvent.OnSeeAllClick -> navigate(
                Route.AllMovies(
                    referenceId = null,
                    event.movieCategory
                )
            )

            is HomeEvent.RefreshFavorites -> getFavorites()
        }
    }

    private suspend fun getGenres() {
        when (val result = genreUseCase()) {
            is Resource.Success -> {
                genreCache.setGenres(result.data.genres)
            }

            is Resource.Error -> {
                _uiEvent.emit(HomeUiEvent.ShowSnackBar(result.message))
            }
        }
    }

    private fun getTrendingMovies() {
        viewModelScope.launch {
            when (val result = trendingMoviesUseCase()) {
                is Resource.Success -> {
                    val movies = result.data.results.take(10).map { movie ->
                        movie.copy(genreNames = genreCache.mapIdsToNames(movie.genreIds))
                    }
                    _state.value = _state.value.copy(
                        trendingMovies = movies.take(10)
                    )
                }

                is Resource.Error -> {
                    _uiEvent.emit(HomeUiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            when (val result = popularMoviesUseCase()) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        popularMovies = result.data.results.take(10),
                        hasMorePopularMovies = result.data.results.size > 10
                    )
                }

                is Resource.Error -> {
                    _uiEvent.emit(HomeUiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            when (val result = nowPlayingUseCase()) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        nowPlayingMovies = result.data.results.take(10),
                        hasMoreNowPlayingMovies = result.data.results.size > 10
                    )
                }

                is Resource.Error -> {
                    _uiEvent.emit(HomeUiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            when (val result = upcomingUseCase()) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        upcomingMovies = result.data.results.take(10),
                        hasMoreUpcomingMovies = result.data.results.size > 10
                    )
                }

                is Resource.Error -> {
                    _uiEvent.emit(HomeUiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

    private fun getFavorites() {
        viewModelScope.launch {
            val favoriteIds = getFavoritesUseCase()
            getFavoriteMovies(favoriteIds)
        }
    }

    private fun getFavoriteMovies(ids: List<Int>) {
        viewModelScope.launch {
            when (val result = favoriteMoviesUseCase(ids)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        favoriteMovies = result.data.take(10),
                        hasMoreFavoriteMovies = result.data.size > 10
                    )
                }

                is Resource.Error -> {
                    _uiEvent.emit(HomeUiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

    private fun navigate(route: Route) {
        viewModelScope.launch {
            _uiEvent.emit(HomeUiEvent.NavigateTo(route))
        }
    }
}