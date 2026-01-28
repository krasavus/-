package com.example.sleepapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.sleepapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundLibraryScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(MidnightStart, MidnightEnd)
                )
            )
    ) {
        item {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /* TODO: Navigate back */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("Sound Library", style = MaterialTheme.typography.titleLarge)
                IconButton(onClick = { /* TODO: Filter */ }) {
                    Icon(Icons.Default.Tune, contentDescription = "Filter")
                }
            }
        }

        item {
            // Search Bar
            SearchBar()
        }

        item {
            // Recently Played
            SectionHeader(title = "Recently Played", onSeeAll = {})
            RecentlyPlayed()
        }

        item {
            // Categories
            SectionHeader(title = "Categories", onSeeAll = {})
            CategoriesGrid()
        }
    }
}

@Composable
fun SearchBar() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = { Text("Search sounds, stories...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Primary
        )
    )
}

@Composable
fun SectionHeader(title: String, onSeeAll: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
        TextButton(onClick = onSeeAll) {
            Text("See All", color = Primary)
        }
    }
}

@Composable
fun RecentlyPlayed() {
    val items = listOf(
        "https://lh3.googleusercontent.com/aida-public/AB6AXuDQi3azdua6pT1DX94ZKVORGmyh2qfSXivoqYQB1cj5PlkM15Mp8VvugkmgkdN9xwFEgyoVmiIxF-367_0fdPNT_6CJkegyGLATwbBXE9DhyLwwCvBNKr7hk1k8wDc-Z9RUIIzbxo7beAj83_G2kO7V1de-p-3r43PyDohsSTcCkyLqmQTLQmAwhfI8pgAlxmLgD_W04zFDBw9icsA8K9j_pZ00f7Wpp_D5J3yEOo825rC8Iz9uhqIRhJwn-fzronhr3zR-ExqsVuY" to "Heavy Rain",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuAvZMAIGDW_cS6Xxw83cuGrm8qTxk2BZgRCUmWk8mQur-ajGl8boJnQFv7cgSXahACRcYM185Kp0wEbDdtqlpLe8qC7HJ9KCUsprkzzjAiYzY3TV_7rfgPKOvUey1azd2kOqKyAT8D8UfLvxp9Ce4MySamjV3uoza9wSO8yTTR1FJr_GwfBi7ubNKzjwFxtmJKCQMmBEWZvb7pAW2iVz285IyR-PpahLKhDsh3749DT6GU553fR8LN_7gFai3MTz9t-XCL_h0Zoobg" to "Deep Sleep",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuC7NzpG5cOaZymOfJhhDGpsNX2rlzLttNn8XEYYGz65gR0u26ykVotdmlcLZk1U0eyBiqirJQcVSiCqUnhH9IqE0IkO-mJcMBY5iZIYxxfZrNTWfr9ycTWRKxFLW5nNPfl6xoQfKjZJU7bMPSo-oC9blI77HBwsZqviHqdNf6-hhn6X9zhC4lNPE8ztdVcwy07PBZGtzpaozznjy_pPtAGXKtt6xhv_PeLfz0j5HspLqAF2Dt5OQYv-7BMsn9e2nC0y5MNl8TCMx2g" to "Forest Night"
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items.size) { index ->
            Column(
                modifier = Modifier.width(140.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(items[index].first)
                        .crossfade(true)
                        .build(),
                    contentDescription = items[index].second,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(140.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Text(
                    items[index].second,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun CategoriesGrid() {
    val categories = listOf(
        "Rain" to Icons.Default.WaterDrop,
        "Forest" to Icons.Default.Forest,
        "White Noise" to Icons.Default.Grain,
        "Meditations" to Icons.Default.SelfImprovement
    )
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        categories.chunked(2).forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { (title, icon) ->
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardDark)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = title,
                                tint = Primary,
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Primary.copy(alpha = 0.1f), CircleShape)
                                    .padding(8.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(title, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                }
            }
        }
    }
}
