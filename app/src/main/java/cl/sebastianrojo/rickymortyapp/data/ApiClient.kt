package cl.sebastianrojo.rickymortyapp.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient {

    // https://rickandmortyapi.com/api/

    // Moshi --> transformar JSON -> data class
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // Retrofit ejecuta request(solicitudes o peticiones) HTTP
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    // implementar la interfaz
    val api: RickMortyApi = retrofit.create(RickMortyApi::class.java)
}