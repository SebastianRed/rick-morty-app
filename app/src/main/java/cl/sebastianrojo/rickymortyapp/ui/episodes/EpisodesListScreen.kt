package cl.sebastianrojo.rickymortyapp.ui.episodes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cl.sebastianrojo.rickymortyapp.data.EpisodeDTO
import cl.sebastianrojo.rickymortyapp.ui.state.UiState

// ── Paleta ──────────────────────────────────────────────────────────────────
private val BackgroundDark  = Color(0xFF0D0F12)
private val SurfaceDark     = Color(0xFF161B22)
private val CardDark        = Color(0xFF1C2330)
private val GreenAccent     = Color(0xFF8BE44A)
private val GreenSelected   = Color(0xFF7AD03A)
private val TextPrimary     = Color(0xFFEEEEEE)
private val TextSecondary   = Color(0xFF8B9099)
private val ChipUnselected  = Color(0xFF1E2530)
private val ChipBorder      = Color(0xFF2C3440)
private val DividerColor    = Color(0xFF1E2530)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodesListScreen() {

    // ── Lógica sin cambios ───────────────────────────────────────────────────
    val vm = remember { EpisodesListViewModel() }

    val state by vm.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { vm.load() }

    // ── Estado local solo para UI (filtro de temporada) ──────────────────────
    var selectedSeason by remember { mutableStateOf(1) }

    // Derivar la lista de temporadas disponibles cuando tengamos datos
    val allEpisodes = (state as? UiState.Success)?.data ?: emptyList()
    val seasons = remember(allEpisodes) {
        allEpisodes.map { it.episode.take(3).removePrefix("S").toIntOrNull() ?: 1 }
            .distinct()
            .sorted()
    }
    val filteredEpisodes = remember(allEpisodes, selectedSeason) {
        allEpisodes.filter { ep ->
            ep.episode.startsWith("S%02d".format(selectedSeason))
        }
    }

    // ── UI ───────────────────────────────────────────────────────────────────
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // ── Top Bar ──────────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackgroundDark)
                    .padding(horizontal = 20.dp)
                    .padding(top = 52.dp, bottom = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Rick & Morty",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        letterSpacing = (-0.5).sp
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = TextPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        // Filter icon (tres líneas horizontales)
                        FilterIcon(tint = TextPrimary)
                    }
                }
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "Episodes",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = GreenAccent,
                    letterSpacing = 0.3.sp
                )
            }

            // ── Season chips ─────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val chipSeasons = if (seasons.isNotEmpty()) seasons else (1..7).toList()
                chipSeasons.forEach { season ->
                    SeasonChip(
                        season = season,
                        isSelected = season == selectedSeason,
                        onClick = { selectedSeason = season }
                    )
                }
            }

            // ── Divider ───────────────────────────────────────────────────────
            HorizontalDivider(
                thickness = 1.dp,
                color = DividerColor,
                modifier = Modifier.padding(horizontal = 0.dp)
            )

            // ── Content ───────────────────────────────────────────────────────
            when (val s = state) {
                UiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = GreenAccent)
                    }
                }
                is UiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Error: ${s.message}",
                            color = TextSecondary,
                            fontSize = 14.sp
                        )
                    }
                }
                is UiState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(filteredEpisodes) { episode ->
                            EpisodeCard(episode = episode, onClick = { /* navegar */ })
                        }
                    }
                }
            }
        }
    }
}

// ── Season Chip ──────────────────────────────────────────────────────────────
@Composable
private fun SeasonChip(season: Int, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (isSelected) GreenSelected else ChipUnselected)
            .clickable(onClick = onClick)
            .then(
                if (!isSelected) Modifier.clip(RoundedCornerShape(50)) else Modifier
            )
            .padding(horizontal = 18.dp, vertical = 9.dp)
    ) {
        Text(
            text = "Season $season",
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color(0xFF0D1A00) else TextSecondary,
            letterSpacing = 0.2.sp
        )
    }
}

// ── Episode Card ─────────────────────────────────────────────────────────────
@Composable
private fun EpisodeCard(episode: EpisodeDTO, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardDark)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Column {
            // Badge de episodio (ej: S01E01)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF2D4A1A))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = episode.episode,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GreenAccent,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(Modifier.height(8.dp))

            // Nombre del episodio + fecha + flecha
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = episode.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary,
                    modifier = Modifier.weight(1f),
                    letterSpacing = (-0.2).sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = episode.air_date,
                        fontSize = 12.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "›",
                        fontSize = 20.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}

// ── Filter Icon (hamburger) ───────────────────────────────────────────────────
@Composable
private fun FilterIcon(tint: Color) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.End,
        modifier = Modifier.size(24.dp).padding(2.dp)
    ) {
        Box(Modifier.fillMaxWidth().height(2.dp).background(tint, RoundedCornerShape(1.dp)))
        Box(Modifier.fillMaxWidth(0.75f).height(2.dp).background(tint, RoundedCornerShape(1.dp)))
        Box(Modifier.fillMaxWidth(0.5f).height(2.dp).background(tint, RoundedCornerShape(1.dp)))
    }
}
