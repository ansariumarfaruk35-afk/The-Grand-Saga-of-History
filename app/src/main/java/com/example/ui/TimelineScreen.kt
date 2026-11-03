package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.HistoryRepository
import com.example.data.TimelineEvent
import com.example.ui.theme.DarkBg
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MartelFontFamily
import com.example.ui.theme.TerracottaAccent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineScreen(
    onNavigateToChapter: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedEraFilter by remember { mutableStateOf("सबै") }
    val masterTimeline = HistoryRepository.masterTimeline

    val eraFilters = listOf("सबै", "नेपाल इतिहास", "प्राचीन काल", "साम्राज्य", "महायुद्ध", "आधुनिक विश्व")

    val filteredEvents = remember(selectedEraFilter) {
        if (selectedEraFilter == "सबै") {
            masterTimeline
        } else {
            masterTimeline.filter { it.era.contains(selectedEraFilter) }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "मानव सभ्यताको समयरेखा (Timeline)",
                            fontFamily = MartelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "४.५ अर्ब वर्ष पूर्वदेखि २१औँ शताब्दी र भविष्यसम्म",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Era filter chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(eraFilters) { era ->
                    FilterChip(
                        selected = selectedEraFilter == era,
                        onClick = { selectedEraFilter = era },
                        label = { Text(era) },
                        colors = if (era == "नेपाल इतिहास") {
                            FilterChipDefaults.filterChipColors(
                                selectedContainerColor = TerracottaAccent.copy(alpha = 0.2f),
                                selectedLabelColor = TerracottaAccent
                            )
                        } else FilterChipDefaults.filterChipColors()
                    )
                }
            }

            // Timeline Items
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(top = 12.dp, bottom = 40.dp)
            ) {
                items(filteredEvents) { event ->
                    TimelineItemRow(event = event)
                }
            }
        }
    }
}

@Composable
fun TimelineItemRow(event: TimelineEvent) {
    val isNepal = event.era.contains("नेपाल")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Timeline Dot & Line
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(36.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(if (isNepal) TerracottaAccent else GoldPrimary)
                    .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape)
            )
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(80.dp)
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Content Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isNepal) TerracottaAccent.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surface
            ),
            border = BorderStroke(1.dp, if (isNepal) TerracottaAccent.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (isNepal) TerracottaAccent else GoldPrimary
                    ) {
                        Text(
                            text = event.year,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = if (isNepal) Color.White else DarkBg,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }

                    if (event.era.isNotEmpty()) {
                        Text(
                            text = event.era,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isNepal) TerracottaAccent else GoldPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = event.title,
                    fontFamily = MartelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = event.description,
                    style = MaterialTheme.typography.bodySmall,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
