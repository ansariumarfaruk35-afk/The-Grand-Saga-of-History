package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BookPart
import com.example.data.Chapter
import com.example.data.HistoryRepository
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MartelFontFamily
import com.example.ui.theme.TerracottaAccent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableOfContentsScreen(
    onSelectChapter: (Int) -> Unit,
    onOpenIntroduction: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedPartFilter by remember { mutableStateOf<Int?>(null) } // null means all
    var expandedPartNumbers by remember { mutableStateOf(setOf(1, 4)) } // default expand Part 1 and Part 4 Nepal

    val allParts = HistoryRepository.allParts

    val filteredParts = remember(searchQuery, selectedPartFilter) {
        val q = searchQuery.trim().lowercase()
        allParts.mapNotNull { part ->
            if (selectedPartFilter != null && part.partNumber != selectedPartFilter) {
                null
            } else if (q.isEmpty()) {
                part
            } else {
                val matchingChapters = part.chapters.filter { ch ->
                    ch.titleDevanagari.lowercase().contains(q) ||
                    ch.titleEnglish.lowercase().contains(q) ||
                    ch.subheadings.any { it.lowercase().contains(q) } ||
                    part.titleDevanagari.lowercase().contains(q)
                }
                if (matchingChapters.isNotEmpty() || part.titleDevanagari.lowercase().contains(q)) {
                    part.copy(chapters = if (matchingChapters.isNotEmpty()) matchingChapters else part.chapters)
                } else null
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "सम्पूर्ण विषयसूची (Table of Contents)",
                            fontFamily = MartelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp
                        )
                        Text(
                            text = "१० भागहरू र ७८ विस्तृत ऐतिहासिक अध्यायहरू",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onOpenIntroduction,
                        modifier = Modifier.testTag("btn_open_intro")
                    ) {
                        Icon(
                            Icons.Default.MenuBook,
                            contentDescription = "भूमिका पढ्नुहोस्",
                            tint = MaterialTheme.colorScheme.primary
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
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .testTag("search_toc_input"),
                placeholder = { Text("अध्याय, शीर्षक वा विषय खोज्नुहोस् (उदा. पृथ्वीनारायण, पिरामिड)...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
            )

            // Part Filter Chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedPartFilter == null,
                        onClick = { selectedPartFilter = null },
                        label = { Text("सबै भागहरू (All)") },
                        leadingIcon = if (selectedPartFilter == null) {
                            { Icon(Icons.Default.Check, contentDescription = null, Modifier.size(16.dp)) }
                        } else null
                    )
                }
                item {
                    FilterChip(
                        selected = selectedPartFilter == 4,
                        onClick = { selectedPartFilter = if (selectedPartFilter == 4) null else 4 },
                        label = { Text("🇳🇵 नेपाल विशेष (Part IV)") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = TerracottaAccent.copy(alpha = 0.2f),
                            selectedLabelColor = TerracottaAccent
                        )
                    )
                }
                items((1..10).toList()) { partNum ->
                    if (partNum != 4) {
                        FilterChip(
                            selected = selectedPartFilter == partNum,
                            onClick = {
                                selectedPartFilter = if (selectedPartFilter == partNum) null else partNum
                            },
                            label = { Text("भाग $partNum") }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Table of Contents List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                // Intro Banner Card
                item {
                    Card(
                        onClick = onOpenIntroduction,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("card_book_intro")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.AutoStories,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "पुस्तकको भूमिका (Prologue)",
                                    fontFamily = MartelFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "इतिहास अध्ययनको दार्शनिक महत्त्व र मानव सभ्यताको दृष्टिकोण",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Icon(
                                Icons.Default.ArrowForwardIos,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                items(filteredParts) { part ->
                    val isExpanded = expandedPartNumbers.contains(part.partNumber) || searchQuery.isNotEmpty()
                    PartCard(
                        part = part,
                        isExpanded = isExpanded,
                        onToggleExpand = {
                            expandedPartNumbers = if (expandedPartNumbers.contains(part.partNumber)) {
                                expandedPartNumbers - part.partNumber
                            } else {
                                expandedPartNumbers + part.partNumber
                            }
                        },
                        onSelectChapter = onSelectChapter
                    )
                }
            }
        }
    }
}

@Composable
fun PartCard(
    part: BookPart,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onSelectChapter: (Int) -> Unit
) {
    val rotationAngle by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f, label = "arrow_rot")
    val isNepalPart = part.partNumber == 4

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isNepalPart) {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleExpand() }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            if (isNepalPart) TerracottaAccent else MaterialTheme.colorScheme.primary
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${part.partNumber}",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = part.titleDevanagari,
                        fontFamily = MartelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${part.timePeriod} • ${part.chapters.size} अध्यायहरू",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(onClick = onToggleExpand) {
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = "विस्तार गर्नुहोस्",
                        modifier = Modifier.rotate(rotationAngle)
                    )
                }
            }

            // Description
            Text(
                text = part.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
            )

            // Expanded Chapters List with Subheadings
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    part.chapters.forEach { chapter ->
                        ChapterItemView(
                            chapter = chapter,
                            onClick = { onSelectChapter(chapter.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChapterItemView(
    chapter: Chapter,
    onClick: () -> Unit
) {
    var showSubheadings by remember { mutableStateOf(false) }

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("chapter_item_${chapter.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Chapter Number Badge
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                    modifier = Modifier.size(30.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "${chapter.chapterNumber}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = chapter.titleDevanagari,
                        fontFamily = MartelFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${chapter.titleEnglish} • ${chapter.timePeriod}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                IconButton(
                    onClick = { showSubheadings = !showSubheadings },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        if (showSubheadings) Icons.Default.UnfoldLess else Icons.Default.UnfoldMore,
                        contentDescription = "उपशीर्षक हेर्नुहोस्",
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = "अध्याय पढ्नुहोस्",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            // Subheadings Preview
            AnimatedVisibility(visible = showSubheadings) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, top = 8.dp, end = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "अध्यायका मुख्य विषय र उपशीर्षकहरू:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    chapter.subheadings.forEach { sub ->
                        Row(verticalAlignment = Alignment.Top) {
                            Text("• ", color = MaterialTheme.colorScheme.primary, fontSize = 12.sp)
                            Text(
                                text = sub,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
