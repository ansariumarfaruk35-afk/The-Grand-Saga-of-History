package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.Chapter
import com.example.data.HistoryRepository
import com.example.data.PreferencesManager
import com.example.ui.theme.DarkBg
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MartelFontFamily
import com.example.ui.theme.TerracottaAccent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    prefsManager: PreferencesManager,
    onNavigateToChapter: (Int) -> Unit,
    onNavigateToToc: () -> Unit,
    onNavigateToTimeline: () -> Unit,
    onNavigateToQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lastReadChapterId by prefsManager.lastReadChapterId.collectAsState()
    val lastReadChapter = remember(lastReadChapterId) {
        HistoryRepository.getChapterById(lastReadChapterId) ?: HistoryRepository.getAllChapters().first()
    }

    var showIntroDialog by remember { mutableStateOf(false) }

    val featuredChapters = remember {
        listOf(
            HistoryRepository.getChapterById(1)!!,  // Earth origin
            HistoryRepository.getChapterById(7)!!,  // Egypt
            HistoryRepository.getChapterById(16)!!, // Ashoka
            HistoryRepository.getChapterById(26)!!, // Licchavi Nepal
            HistoryRepository.getChapterById(29)!!, // Prithvi Narayan Shah
            HistoryRepository.getChapterById(34)!!, // World War II
            HistoryRepository.getChapterById(50)!!, // Buddha & Hinduism
            HistoryRepository.getChapterById(64)!!, // AI Revolution
            HistoryRepository.getChapterById(78)!!  // Future of Civilization
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = GoldPrimary,
                            modifier = Modifier.size(34.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.AutoStories,
                                    contentDescription = null,
                                    tint = DarkBg,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = HistoryRepository.BOOK_TITLE,
                            fontFamily = MartelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showIntroDialog = true },
                        modifier = Modifier.testTag("btn_home_info")
                    ) {
                        Icon(Icons.Default.Info, contentDescription = "पुस्तकको बारेमा", tint = GoldPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Hero Banner Section
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_history_hero),
                        contentDescription = "विश्व इतिहासको महागाथा",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.5f),
                                        Color.Black.copy(alpha = 0.92f)
                                    )
                                )
                            )
                    )

                    // Hero Content
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Surface(
                            color = GoldPrimary,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "नेपाली देवनागरी संस्करण",
                                color = DarkBg,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = HistoryRepository.BOOK_TITLE,
                            fontFamily = MartelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Color.White
                        )
                        Text(
                            text = HistoryRepository.BOOK_SUBTITLE,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.85f),
                            maxLines = 2
                        )
                    }
                }
            }

            // Quick Stats Grid
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(number = "१०", label = "भागहरू (Parts)", icon = Icons.Default.LibraryBooks, modifier = Modifier.weight(1f))
                    StatCard(number = "७८", label = "अध्यायहरू", icon = Icons.Default.MenuBook, modifier = Modifier.weight(1f))
                    StatCard(number = "४.५ अर्ब", label = "वर्षको यात्रा", icon = Icons.Default.Public, modifier = Modifier.weight(1f))
                    StatCard(number = "नेपाल", label = "विशेष इतिहास", icon = Icons.Default.Flag, modifier = Modifier.weight(1f), isSpecial = true)
                }
            }

            // Continue Reading Card
            item {
                Card(
                    onClick = { onNavigateToChapter(lastReadChapter.id) },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .testTag("card_continue_reading")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(GoldPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = DarkBg,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "पठन जारी राख्नुहोस् (Continue Reading)",
                                style = MaterialTheme.typography.labelSmall,
                                color = GoldPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "अध्याय ${lastReadChapter.chapterNumber}: ${lastReadChapter.titleDevanagari}",
                                fontFamily = MartelFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "भाग ${lastReadChapter.partNumber} • ${lastReadChapter.timePeriod}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Icon(
                            Icons.Default.ArrowForwardIos,
                            contentDescription = null,
                            tint = GoldPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Quick Exploration Actions Row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ActionExploreCard(
                        title = "सम्पूर्ण विषयसूची",
                        subtitle = "७८ वटै अध्यायहरू",
                        icon = Icons.Default.FormatListNumbered,
                        onClick = onNavigateToToc,
                        modifier = Modifier.weight(1f)
                    )
                    ActionExploreCard(
                        title = "समयरेखा",
                        subtitle = "ऐतिहासिक यात्रा",
                        icon = Icons.Default.Timeline,
                        onClick = onNavigateToTimeline,
                        modifier = Modifier.weight(1f)
                    )
                    ActionExploreCard(
                        title = "ज्ञान परीक्षण",
                        subtitle = "इतिहास क्विज",
                        icon = Icons.Default.Quiz,
                        onClick = onNavigateToQuiz,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Featured Chapters Carousel
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "विशेष तथा प्रमुख अध्यायहरू",
                            fontFamily = MartelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        TextButton(onClick = onNavigateToToc) {
                            Text("सबै हेर्नुहोस्", color = GoldPrimary)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(featuredChapters) { chapter ->
                            FeaturedChapterCard(
                                chapter = chapter,
                                onClick = { onNavigateToChapter(chapter.id) }
                            )
                        }
                    }
                }
            }

            // Nepal History Spotlight
            item {
                Card(
                    onClick = { onNavigateToChapter(24) }, // Start ancient Nepal
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = TerracottaAccent.copy(alpha = 0.12f)
                    ),
                    border = BorderStroke(1.5.dp, TerracottaAccent),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🇳🇵", fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "नेपालको गौरवमय इतिहास (भाग ४)",
                                fontFamily = MartelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = TerracottaAccent
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "काठमाडौँ दह सुकेको समयदेखि किरात यलम्बर, लिच्छवि स्वर्ण युग (मानदेव/अंशुवर्मा), मल्ल कालका तीन राज्य, पृथ्वीनारायण शाहको एकीकरण अभियान, १०४ वर्षे राणा शासन र आधुनिक लोकतान्त्रिक गणतन्त्रसम्मको सम्पूर्ण प्रामाणिक अध्ययन।",
                            style = MaterialTheme.typography.bodySmall,
                            lineHeight = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "नेपाल खण्ड पढ्नुहोस् (अध्याय २४ देखि ३२) →",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = TerracottaAccent
                            )
                        }
                    }
                }
            }
        }
    }

    // Grand Introduction Dialog
    if (showIntroDialog) {
        AlertDialog(
            onDismissRequest = { showIntroDialog = false },
            title = {
                Text(
                    text = "पुस्तकको भूमिका (Prologue)",
                    fontFamily = MartelFontFamily,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                LazyColumn(modifier = Modifier.heightIn(max = 350.dp)) {
                    item {
                        Text(
                            text = HistoryRepository.BOOK_INTRODUCTION.trim(),
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 24.sp
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showIntroDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                ) {
                    Text("बुझेँ (Continue)", color = DarkBg)
                }
            }
        )
    }
}

@Composable
fun StatCard(
    number: String,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    isSpecial: Boolean = false
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isSpecial) TerracottaAccent.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
        border = BorderStroke(1.dp, if (isSpecial) TerracottaAccent.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (isSpecial) TerracottaAccent else GoldPrimary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = number,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = if (isSpecial) TerracottaAccent else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = label,
                fontSize = 10.sp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ActionExploreCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.height(84.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1)
            Text(subtitle, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
        }
    }
}

@Composable
fun FeaturedChapterCard(
    chapter: Chapter,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
        modifier = Modifier
            .width(220.dp)
            .height(170.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = GoldPrimary.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "भाग ${chapter.partNumber}",
                            color = GoldPrimary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        text = "अध्याय ${chapter.chapterNumber}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = chapter.titleDevanagari,
                    fontFamily = MartelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = chapter.timePeriod,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp),
                    maxLines = 1
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("अध्ययन गर्नुहोस्", fontSize = 11.sp, color = GoldPrimary, fontWeight = FontWeight.Bold)
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
            }
        }
    }
}
