package dev.jmyp.movies.ui.screens.personDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jmyp.movies.data.model.Movie
import dev.jmyp.movies.data.model.PersonDetail
import dev.jmyp.movies.data.network.Resource
import dev.jmyp.movies.domain.usecase.PersonCreditsUseCase
import dev.jmyp.movies.domain.usecase.PersonDetailUseCase
import dev.jmyp.movies.ui.navigation.MovieCategory
import dev.jmyp.movies.ui.navigation.Route
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PersonDetailViewModel(
    private val personId: Int,
    private val personDetailUseCase: PersonDetailUseCase,
    private val personCreditsUseCase: PersonCreditsUseCase
) : ViewModel() {

    data class PersonDetailState(
        val person: PersonDetail? = null,
        val credits: List<Movie>? = null,
        val hasMoreCredits: Boolean = false,
        val isLoading: Boolean = false
    )

    sealed class PersonDetailEvent {
        data class OnMovieClick(val id: Int) : PersonDetailEvent()
        object OnSeeAllClick : PersonDetailEvent()
    }

    sealed class PersonDetailUiEvent {
        data class NavigateTo(val route: Route) : PersonDetailUiEvent()
        data class ShowSnackBar(val message: String) : PersonDetailUiEvent()
    }

    private val _state = MutableStateFlow(PersonDetailState())
    val state: StateFlow<PersonDetailState> = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PersonDetailUiEvent>()
    val uiEvent: SharedFlow<PersonDetailUiEvent> = _uiEvent.asSharedFlow()

    init {
        getPersonDetail()
        getPersonCredits()
    }

    fun onEvent(event: PersonDetailEvent) {
        when (event) {
            is PersonDetailEvent.OnMovieClick -> navigate(Route.MovieDetail(event.id))
            is PersonDetailEvent.OnSeeAllClick -> navigate(
                Route.AllMovies(
                    personId,
                    MovieCategory.PERSON_CREDITS
                )
            )
        }
    }

    private fun getPersonDetail() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            when (val result = personDetailUseCase(personId)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        person = result.data,
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _uiEvent.emit(PersonDetailUiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

    private fun getPersonCredits() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            when (val result = personCreditsUseCase(personId)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        credits = result.data.cast.take(10),
                        hasMoreCredits = result.data.cast.size > 10,
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _uiEvent.emit(PersonDetailUiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

    private fun navigate(route: Route) {
        viewModelScope.launch {
            _uiEvent.emit(PersonDetailUiEvent.NavigateTo(route))
        }
    }
}