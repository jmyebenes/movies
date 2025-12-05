package dev.jmyp.movies.ui.screens.allCredits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jmyp.movies.data.network.Resource
import dev.jmyp.movies.domain.usecase.CreditsUseCase
import dev.jmyp.movies.ui.components.PersonItem
import dev.jmyp.movies.ui.navigation.PersonCategory
import dev.jmyp.movies.ui.navigation.Route
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllCreditsViewModel(
    private val personId: Int,
    private val personCategory: PersonCategory,
    private val creditsUseCase: CreditsUseCase
) : ViewModel() {

    data class AllCreditsState(
        val allCredits: List<PersonItem> = emptyList(),
        val isLoading: Boolean = false
    )

    sealed class AllCreditsEvent {
        data class OnPersonClick(val id: Int) : AllCreditsEvent()
    }

    sealed class AllCreditsUiEvent {
        data class NavigateTo(val route: Route) : AllCreditsUiEvent()
        data class ShowSnackBar(val message: String) : AllCreditsUiEvent()
    }

    private val _state = MutableStateFlow(AllCreditsState())
    val state: StateFlow<AllCreditsState> = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<AllCreditsUiEvent>()
    val uiEvent: SharedFlow<AllCreditsUiEvent> = _uiEvent.asSharedFlow()

    init {
        getAllCredits()
    }

    fun onEvent(event: AllCreditsEvent) {
        when (event) {
            is AllCreditsEvent.OnPersonClick -> navigate(Route.PersonDetail(event.id))
        }
    }

    private fun getAllCredits() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            when (val result = creditsUseCase(personId)) {
                is Resource.Success -> {
                    val mappedCredits = when (personCategory) {
                        PersonCategory.CAST -> result.data.cast.map {
                            PersonItem(
                                id = it.id,
                                title = it.name,
                                subtitle = it.character,
                                image = it.profilePath
                            )
                        }

                        PersonCategory.CREW -> result.data.crew.map {
                            PersonItem(
                                id = it.id,
                                title = it.name,
                                subtitle = it.job,
                                image = it.profilePath
                            )
                        }
                    }
                    _state.value = _state.value.copy(
                        allCredits = mappedCredits,
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _uiEvent.emit(AllCreditsUiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }

    private fun navigate(route: Route) {
        viewModelScope.launch {
            _uiEvent.emit(AllCreditsUiEvent.NavigateTo(route))
        }
    }
}