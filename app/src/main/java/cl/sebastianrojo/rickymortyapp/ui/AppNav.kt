package cl.sebastianrojo.rickymortyapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import cl.sebastianrojo.rickymortyapp.ui.episodes.EpisodesListScreen

@Composable
fun AppNav() {
    // controlador que recuerda y crea la navegacion
    val navController = rememberNavController()

    // Definir rutas y el composable para cada una de las rutas
    NavHost(
        navController = navController,
        startDestination = "episodes"
    ) {

        composable("episodes") {
            EpisodesListScreen()
        }


        composable("episode/{id}") {}

    }
}