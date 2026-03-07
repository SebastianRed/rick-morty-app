package cl.sebastianrojo.rickymortyapp.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.sebastianrojo.rickymortyapp.data.ApiClient
import cl.sebastianrojo.rickymortyapp.data.EpisodeDTO
import cl.sebastianrojo.rickymortyapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EpisodesListViewModel : ViewModel() {

    // MutableStateFlow: "estado mutable interno" que la VM actualiza
    private val _state = MutableStateFlow<UiState<List<EpisodeDTO>>>(UiState.Loading)

    // StateFlow: "Estado de lectura" -> UI observa
    val state: StateFlow<UiState<List<EpisodeDTO>>> = _state

    fun load() {
        // 1 - Loading
        _state.value = UiState.Loading

        // 2 - Lanzar ciclo de vida (coroutine)
        viewModelScope.launch {
            // try catch
            runCatching {
                // 3 - LLamo a la api
                ApiClient.api.getEspisodes().results
            }.onSuccess { episodes ->
                // 4 - Si funciona, Publicamos los datos
                _state.value = UiState.Success(episodes)
            }.onFailure { error ->
                // 5 - Si falla, publicamos el error
                _state.value = UiState.Error(error.message ?: "Error desconocido" )
            }
        }
    }

}