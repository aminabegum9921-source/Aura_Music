package com.example.adfreemusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Track(val title: String, val artist: String, val downloaded: Boolean = false)

private val Bg = Color(0xFF09090B)
private val Card = Color(0xFF15151A)
private val Accent = Color(0xFFB8FF4A)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AuraApp() }
    }
}

@Composable
fun AuraApp() {
    var selected by remember { mutableIntStateOf(0) }
    var playing by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    var tracks by remember {
        mutableStateOf(listOf(
            Track("Midnight Drive", "Aura Originals"),
            Track("Neon Rain", "Luna Vale"),
            Track("Afterglow", "Northline"),
            Track("Ocean Lights", "Mira Skye"),
            Track("Dream State", "Kairo")
        ))
    }

    MaterialTheme(colorScheme = darkColorScheme(
        background = Bg, surface = Card, primary = Accent, onPrimary = Color.Black,
        onBackground = Color.White, onSurface = Color.White
    )) {
        Scaffold(
            containerColor = Bg,
            bottomBar = {
                NavigationBar(containerColor = Color(0xFF101014)) {
                    val items = listOf(Icons.Default.Home to "Home", Icons.Default.Search to "Search",
                        Icons.Default.Download to "Downloads", Icons.Default.Favorite to "Liked")
                    items.forEachIndexed { i, pair ->
                        NavigationBarItem(
                            selected = selected == i,
                            onClick = { selected = i },
                            icon = { Icon(pair.first, null) },
                            label = { Text(pair.second) }
                        )
                    }
                }
            }
        ) { pad ->
            Column(Modifier.fillMaxSize().padding(pad).padding(horizontal = 20.dp)) {
                Spacer(Modifier.height(18.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("AURA", color = Accent, fontSize = 13.sp, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
                        Text("Your music, your way.", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                    }
                    IconButton(onClick = {}) { Icon(Icons.Default.Settings, "Settings") }
                }
                Spacer(Modifier.height(20.dp))

                when (selected) {
                    0 -> HomeScreen(tracks, playing, { playing = !playing })
                    1 -> SearchScreen(query, { query = it }, tracks, { playing = true })
                    2 -> DownloadsScreen(tracks)
                    else -> LikedScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen(tracks: List<Track>, playing: Boolean, toggle: () -> Unit) {
    Text("Good afternoon", color = Color.LightGray)
    Spacer(Modifier.height(14.dp))
    Box(
        Modifier.fillMaxWidth().height(190.dp).clip(RoundedCornerShape(28.dp))
            .background(Brush.linearGradient(listOf(Color(0xFF304A12), Color(0xFF17191A))))
            .padding(22.dp)
    ) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Text("AD-FREE
LISTENING", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("No ads. No interruptions.", color = Color.LightGray, Modifier.weight(1f))
                FilledIconButton(onClick = toggle) {
                    Icon(if (playing) Icons.Default.Pause else Icons.Default.PlayArrow, null)
                }
            }
        }
    }
    Spacer(Modifier.height(24.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Made for you", fontSize = 21.sp, fontWeight = FontWeight.Bold, Modifier.weight(1f))
        Text("See all", color = Accent)
    }
    Spacer(Modifier.height(10.dp))
    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(tracks.take(5)) { track -> TrackRow(track, {}) }
    }
}

@Composable
fun SearchScreen(query: String, setQuery: (String) -> Unit, tracks: List<Track>, play: () -> Unit) {
    OutlinedTextField(
        value = query, onValueChange = setQuery, modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Search songs or artists") },
        leadingIcon = { Icon(Icons.Default.Search, null) },
        singleLine = true, shape = RoundedCornerShape(18.dp)
    )
    Spacer(Modifier.height(18.dp))
    val filtered = tracks.filter { "${it.title} ${it.artist}".contains(query, true) }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(filtered) { TrackRow(it, play) }
    }
}

@Composable
fun DownloadsScreen(tracks: List<Track>) {
    Text("Downloads", fontSize = 25.sp, fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(8.dp))
    Text("Songs saved for offline listening", color = Color.Gray)
    Spacer(Modifier.height(18.dp))
    tracks.filter { it.downloaded }.ifEmpty {
        listOf(Track("No downloads yet", "Use an authorized music source to save tracks offline."))
    }.let { list ->
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(list) { TrackRow(it, {}) }
        }
    }
}

@Composable
fun LikedScreen() {
    Text("Liked songs", fontSize = 25.sp, fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(16.dp))
    Text("Your favorites will appear here.", color = Color.Gray)
}

@Composable
fun TrackRow(track: Track, play: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp)).background(Card).padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(54.dp).clip(RoundedCornerShape(14.dp))
            .background(Brush.linearGradient(listOf(Color(0xFF607D30), Color(0xFF1F2320)))),
            contentAlignment = Alignment.Center) {
            Icon(Icons.Default.MusicNote, null)
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(track.title, fontWeight = FontWeight.SemiBold)
            Text(track.artist, color = Color.Gray, fontSize = 13.sp)
        }
        IconButton(onClick = play) { Icon(Icons.Default.PlayArrow, null) }
        IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, null) }
    }
}
