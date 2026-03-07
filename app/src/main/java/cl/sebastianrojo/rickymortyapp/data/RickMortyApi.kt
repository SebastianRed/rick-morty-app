package cl.sebastianrojo.rickymortyapp.data

import retrofit2.http.GET

interface RickMortyApi {

    // - esta anotacion le indica a Retrofit:
    // * Cuando se llame este metodo, ejecute un HTTP GET

    // https://rickandmortyapi.com/api/episode

    // suspend:
    // * ejecutar sin bloquear el hilo principal
    @GET("episode")
    suspend fun getEspisodes() : EpisodesResponseDTO
}