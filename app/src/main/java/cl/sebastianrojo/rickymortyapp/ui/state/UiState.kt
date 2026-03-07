package cl.sebastianrojo.rickymortyapp.ui.state

// Loading: mostrando un spinner, texto de cargando
// Error: hubo un problema
// Success: datos estan listos
sealed interface UiState<out T> {
    data object Loading: UiState<Nothing>
    data class Error(val message : String) : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
}