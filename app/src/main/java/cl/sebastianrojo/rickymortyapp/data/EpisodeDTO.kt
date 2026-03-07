package cl.sebastianrojo.rickymortyapp.data

data class EpisodesResponseDTO(
    val results: List<EpisodeDTO>
)

data class EpisodeDTO (
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
)