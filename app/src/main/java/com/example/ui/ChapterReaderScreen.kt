package com.example.ui

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.theme.*
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterReaderScreen(
    chapterId: Int,
    prefsManager: PreferencesManager,
    onNavigateBack: () -> Unit,
    onNavigateToChapter: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val chapter = remember(chapterId) {
        HistoryRepository.getChapterById(chapterId) ?: HistoryRepository.getAllChapters().first()
    }

    val bookmarkedChapterIds by prefsManager.bookmarkedChapterIds.collectAsState()
    val isBookmarked = bookmarkedChapterIds.contains(chapter.id)

    val currentTheme by prefsManager.theme.collectAsState()
    val fontSizeSp by prefsManager.fontSizeSp.collectAsState()
    val useSerif by prefsManager.useSerif.collectAsState()

    var showSettingsSheet by remember { mutableStateOf(false) }
    var isTtsPlaying by remember { mutableStateOf(false) }

    // TextToSpeech setup
    var tts: TextToSpeech? by remember { mutableStateOf(null) }

    DisposableEffect(Unit) {
        val ttsInstance = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // Try Nepali locale, fallback to Hindi or Default
                val nepaliLocale = Locale("ne", "NP")
                val result = tts?.setLanguage(nepaliLocale)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts?.language = Locale("hi", "IN")
                }
            }
        }
        tts = ttsInstance

        onDispose {
            ttsInstance.stop()
            ttsInstance.shutdown()
        }
    }

    LaunchedEffect(chapterId) {
        prefsManager.setLastReadChapter(chapterId)
    }

    // Color theme values
    val (bgColor, surfaceColor, textColor, textMutedColor, borderColor) = when (currentTheme) {
        ReadingTheme.PARCHMENT -> Tuple5(ParchmentBg, ParchmentSurface, InkDark, InkMuted, ParchmentBorder)
        ReadingTheme.SEPIA -> Tuple5(SepiaBg, SepiaSurface, SepiaText, SepiaMuted, Color(0xFFDECDB4))
        ReadingTheme.DARK -> Tuple5(DarkBg, DarkSurface, DarkText, DarkTextMuted, Color(0xFF38312B))
    }

    val activeFontFamily = if (useSerif) MartelFontFamily else MuktaFontFamily
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "भाग ${chapter.partNumber} • अध्याय ${chapter.chapterNumber}",
                            style = MaterialTheme.typography.labelSmall,
                            color = GoldPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = chapter.titleDevanagari,
                            fontFamily = MartelFontFamily,
                            fontSize = 16.sp,
                            maxLines = 1,
                            color = textColor
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "फर्कनुहोस्", tint = textColor)
                    }
                },
                actions = {
                    // TTS Audio Button
                    IconButton(
                        onClick = {
                            if (isTtsPlaying) {
                                tts?.stop()
                                isTtsPlaying = false
                            } else {
                                val textToRead = "${chapter.titleDevanagari}। ${chapter.historicalBackground}। ${chapter.detailedExplanation}। ${chapter.powerfulConclusion}"
                                tts?.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, "chapter_${chapter.id}")
                                isTtsPlaying = true
                            }
                        },
                        modifier = Modifier.testTag("btn_reader_tts")
                    ) {
                        Icon(
                            if (isTtsPlaying) Icons.Default.VolumeUp else Icons.Default.VolumeMute,
                            contentDescription = "सुन्नुहोस्",
                            tint = if (isTtsPlaying) GoldPrimary else textColor
                        )
                    }

                    // Bookmark
                    IconButton(
                        onClick = { prefsManager.toggleBookmark(chapter.id) },
                        modifier = Modifier.testTag("btn_reader_bookmark")
                    ) {
                        Icon(
                            if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "बुकमार्क",
                            tint = if (isBookmarked) GoldPrimary else textColor
                        )
                    }

                    // Reader Settings Button
                    IconButton(
                        onClick = { showSettingsSheet = true },
                        modifier = Modifier.testTag("btn_reader_settings")
                    ) {
                        Icon(Icons.Default.FormatSize, contentDescription = "फन्ट र पृष्ठ आकार", tint = textColor)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = surfaceColor
                )
            )
        },
        bottomBar = {
            // Next / Previous Chapter Navigation Bar
            Surface(
                color = surfaceColor,
                tonalElevation = 6.dp,
                border = BorderStroke(1.dp, borderColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val prevChapter = HistoryRepository.getChapterById(chapter.id - 1)
                    val nextChapter = HistoryRepository.getChapterById(chapter.id + 1)

                    TextButton(
                        onClick = {
                            tts?.stop()
                            isTtsPlaying = false
                            prevChapter?.let { onNavigateToChapter(it.id) }
                        },
                        enabled = prevChapter != null
                    ) {
                        Icon(Icons.Default.ArrowBackIos, contentDescription = null, Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("अघिल्लो अध्याय", fontSize = 13.sp)
                    }

                    Text(
                        text = "${chapter.chapterNumber} / 78",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = textMutedColor
                    )

                    Button(
                        onClick = {
                            tts?.stop()
                            isTtsPlaying = false
                            nextChapter?.let { onNavigateToChapter(it.id) }
                        },
                        enabled = nextChapter != null,
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                    ) {
                        Text("अर्को अध्याय", fontSize = 13.sp, color = DarkBg)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.ArrowForwardIos, contentDescription = null, Modifier.size(14.dp), tint = DarkBg)
                    }
                }
            }
        },
        containerColor = bgColor,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Chapter Title Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        color = GoldPrimary.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = "भाग ${chapter.partNumber} • ${chapter.timePeriod}",
                            style = MaterialTheme.typography.labelSmall,
                            color = GoldPrimary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = chapter.titleDevanagari,
                        fontFamily = MartelFontFamily,
                        fontSize = (fontSizeSp + 8).sp,
                        lineHeight = (fontSizeSp + 16).sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = chapter.titleEnglish,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textMutedColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    HorizontalDivider(
                        color = GoldPrimary.copy(alpha = 0.5f),
                        thickness = 2.dp,
                        modifier = Modifier
                            .width(80.dp)
                            .padding(vertical = 12.dp)
                    )

                    // Key Quote Banner
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = surfaceColor
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, borderColor),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.FormatQuote,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = chapter.keyQuote,
                                fontFamily = MartelFontFamily,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                fontSize = (fontSizeSp - 1).sp,
                                lineHeight = (fontSizeSp + 7).sp,
                                color = textColor
                            )
                        }
                    }
                }
            }

            // Subheadings Overview Box
            item {
                SectionCard(
                    title = "अध्यायका मुख्य विषय तथा उपशीर्षकहरू",
                    icon = Icons.Default.ListAlt,
                    surfaceColor = surfaceColor,
                    borderColor = borderColor,
                    textColor = textColor
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        chapter.subheadings.forEach { sub ->
                            Row(verticalAlignment = Alignment.Top) {
                                Text("✦ ", color = GoldPrimary, fontSize = 13.sp)
                                Text(
                                    text = sub,
                                    fontFamily = activeFontFamily,
                                    fontSize = (fontSizeSp - 2).sp,
                                    lineHeight = (fontSizeSp + 5).sp,
                                    color = textColor
                                )
                            }
                        }
                    }
                }
            }

            // 1. Historical Background (ऐतिहासिक पृष्ठभूमि)
            item {
                SectionCard(
                    title = "१. ऐतिहासिक पृष्ठभूमि (Historical Background)",
                    icon = Icons.Default.HistoryEdu,
                    surfaceColor = surfaceColor,
                    borderColor = borderColor,
                    textColor = textColor
                ) {
                    Text(
                        text = chapter.historicalBackground,
                        fontFamily = activeFontFamily,
                        fontSize = fontSizeSp.sp,
                        lineHeight = (fontSizeSp * 1.6f).sp,
                        color = textColor
                    )
                }
            }

            // 2. Detailed Historical Explanation (विस्तृत ऐतिहासिक व्याख्या)
            item {
                SectionCard(
                    title = "२. विस्तृत ऐतिहासिक व्याख्या (In-Depth Historical Analysis)",
                    icon = Icons.Default.AutoStories,
                    surfaceColor = surfaceColor,
                    borderColor = borderColor,
                    textColor = textColor
                ) {
                    Text(
                        text = chapter.detailedExplanation,
                        fontFamily = activeFontFamily,
                        fontSize = fontSizeSp.sp,
                        lineHeight = (fontSizeSp * 1.65f).sp,
                        color = textColor
                    )
                }
            }

            // 3. Important Historical Personalities (महत्त्वपूर्ण ऐतिहासिक व्यक्तित्वहरू)
            if (chapter.importantPersonalities.isNotEmpty()) {
                item {
                    SectionCard(
                        title = "३. महत्त्वपूर्ण ऐतिहासिक व्यक्तित्वहरू (Key Personalities)",
                        icon = Icons.Default.Person,
                        surfaceColor = surfaceColor,
                        borderColor = borderColor,
                        textColor = textColor
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            chapter.importantPersonalities.forEach { p ->
                                Surface(
                                    color = bgColor.copy(alpha = 0.7f),
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.dp, borderColor),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            text = p.name,
                                            fontFamily = MartelFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = (fontSizeSp + 1).sp,
                                            color = GoldPrimary
                                        )
                                        Text(
                                            text = p.role,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = textMutedColor
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = p.description,
                                            fontFamily = activeFontFamily,
                                            fontSize = (fontSizeSp - 1).sp,
                                            lineHeight = (fontSizeSp + 6).sp,
                                            color = textColor
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 4. Major Events and Timelines (प्रमुख घटनाहरू र समयरेखा)
            if (chapter.timelineEvents.isNotEmpty()) {
                item {
                    SectionCard(
                        title = "४. प्रमुख घटनाहरू र समयरेखा (Chronology & Timeline)",
                        icon = Icons.Default.Timeline,
                        surfaceColor = surfaceColor,
                        borderColor = borderColor,
                        textColor = textColor
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            chapter.timelineEvents.forEach { ev ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Surface(
                                        color = GoldPrimary,
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.padding(top = 2.dp)
                                    ) {
                                        Text(
                                            text = ev.year,
                                            color = DarkBg,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = ev.title,
                                            fontFamily = MartelFontFamily,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = fontSizeSp.sp,
                                            color = textColor
                                        )
                                        Text(
                                            text = ev.description,
                                            fontFamily = activeFontFamily,
                                            fontSize = (fontSizeSp - 2).sp,
                                            color = textMutedColor
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 5. Causes and Contributing Factors (कारण तथा कारक तत्वहरू)
            if (chapter.causesAndFactors.isNotEmpty()) {
                item {
                    SectionCard(
                        title = "५. ऐतिहासिक कारण तथा कारक तत्वहरू (Causes & Factors)",
                        icon = Icons.Default.Lightbulb,
                        surfaceColor = surfaceColor,
                        borderColor = borderColor,
                        textColor = textColor
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            chapter.causesAndFactors.forEachIndexed { i, cause ->
                                Row(verticalAlignment = Alignment.Top) {
                                    Text("${i + 1}. ", fontWeight = FontWeight.Bold, color = GoldPrimary)
                                    Text(
                                        text = cause,
                                        fontFamily = activeFontFamily,
                                        fontSize = fontSizeSp.sp,
                                        lineHeight = (fontSizeSp * 1.5f).sp,
                                        color = textColor
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 6. Consequences and Impact (परिणाम र ऐतिहासिक प्रभाव)
            if (chapter.consequencesAndImpact.isNotEmpty()) {
                item {
                    SectionCard(
                        title = "६. परिणाम र ऐतिहासिक प्रभाव (Consequences & Impact)",
                        icon = Icons.Default.Public,
                        surfaceColor = surfaceColor,
                        borderColor = borderColor,
                        textColor = textColor
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            chapter.consequencesAndImpact.forEachIndexed { i, con ->
                                Row(verticalAlignment = Alignment.Top) {
                                    Text("${i + 1}. ", fontWeight = FontWeight.Bold, color = TerracottaAccent)
                                    Text(
                                        text = con,
                                        fontFamily = activeFontFamily,
                                        fontSize = fontSizeSp.sp,
                                        lineHeight = (fontSizeSp * 1.5f).sp,
                                        color = textColor
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 7. Interesting Historical Facts (रोचक ऐतिहासिक तथ्यहरू)
            if (chapter.interestingFacts.isNotEmpty()) {
                item {
                    SectionCard(
                        title = "७. रोचक ऐतिहासिक तथ्यहरू (Fascinating Facts)",
                        icon = Icons.Default.Psychology,
                        surfaceColor = surfaceColor,
                        borderColor = borderColor,
                        textColor = textColor
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            chapter.interestingFacts.forEach { fact ->
                                Row(verticalAlignment = Alignment.Top) {
                                    Icon(
                                        Icons.Default.Star,
                                        contentDescription = null,
                                        tint = GoldPrimary,
                                        modifier = Modifier
                                            .size(16.dp)
                                            .padding(top = 4.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = fact,
                                        fontFamily = activeFontFamily,
                                        fontSize = (fontSizeSp - 1).sp,
                                        lineHeight = (fontSizeSp * 1.5f).sp,
                                        color = textColor
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 8. Historians' Perspectives (इतिहासकारहरूका दृष्टिकोण)
            item {
                SectionCard(
                    title = "८. इतिहासकारहरूका दृष्टिकोण (Historians' Perspectives)",
                    icon = Icons.Default.RecordVoiceOver,
                    surfaceColor = surfaceColor,
                    borderColor = borderColor,
                    textColor = textColor
                ) {
                    Text(
                        text = chapter.historiansPerspectives,
                        fontFamily = activeFontFamily,
                        fontSize = fontSizeSp.sp,
                        lineHeight = (fontSizeSp * 1.6f).sp,
                        color = textColor
                    )
                }
            }

            // 9. Modern World Connection (आधुनिक विश्वसँग सम्बन्ध)
            item {
                SectionCard(
                    title = "९. आधुनिक विश्वसँग सम्बन्ध (Relevance to Today's World)",
                    icon = Icons.Default.Language,
                    surfaceColor = surfaceColor,
                    borderColor = borderColor,
                    textColor = textColor
                ) {
                    Text(
                        text = chapter.modernWorldConnection,
                        fontFamily = activeFontFamily,
                        fontSize = fontSizeSp.sp,
                        lineHeight = (fontSizeSp * 1.6f).sp,
                        color = textColor
                    )
                }
            }

            // 10. Lessons Humanity Can Learn (मानवताले सिक्नुपर्ने पाठहरू)
            if (chapter.lessonsForHumanity.isNotEmpty()) {
                item {
                    SectionCard(
                        title = "१०. मानवताले सिक्नुपर्ने अमर पाठहरू (Lessons for Humanity)",
                        icon = Icons.Default.Spa,
                        surfaceColor = surfaceColor,
                        borderColor = borderColor,
                        textColor = textColor
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            chapter.lessonsForHumanity.forEach { lesson ->
                                Row(verticalAlignment = Alignment.Top) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Color(0xFF388E3C),
                                        modifier = Modifier
                                            .size(18.dp)
                                            .padding(top = 3.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = lesson,
                                        fontFamily = activeFontFamily,
                                        fontSize = fontSizeSp.sp,
                                        lineHeight = (fontSizeSp * 1.55f).sp,
                                        color = textColor
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 11. Powerful Conclusion (निष्कर्ष)
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = GoldPrimary.copy(alpha = 0.12f)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.5.dp, GoldPrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.WorkspacePremium, contentDescription = null, tint = GoldPrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "११. निष्कर्ष (Powerful Conclusion)",
                                fontFamily = MartelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = (fontSizeSp + 1).sp,
                                color = GoldPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = chapter.powerfulConclusion,
                            fontFamily = MartelFontFamily,
                            fontSize = fontSizeSp.sp,
                            lineHeight = (fontSizeSp * 1.65f).sp,
                            fontWeight = FontWeight.Medium,
                            color = textColor
                        )
                    }
                }
            }
        }
    }

    // Reader Settings Modal BottomSheet
    if (showSettingsSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSettingsSheet = false },
            containerColor = surfaceColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "पठन अनुकूलन (Reading Settings)",
                    fontFamily = MartelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = textColor
                )

                // Theme Switcher
                Text("पृष्ठ र रंग शैली (Theme):", style = MaterialTheme.typography.labelMedium, color = textMutedColor)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ThemeOptionButton(
                        label = "पाण्डुलिपि",
                        isSelected = currentTheme == ReadingTheme.PARCHMENT,
                        bgColor = ParchmentBg,
                        textColor = InkDark,
                        onClick = { prefsManager.setTheme(ReadingTheme.PARCHMENT) },
                        modifier = Modifier.weight(1f)
                    )
                    ThemeOptionButton(
                        label = "सेपिया",
                        isSelected = currentTheme == ReadingTheme.SEPIA,
                        bgColor = SepiaBg,
                        textColor = SepiaText,
                        onClick = { prefsManager.setTheme(ReadingTheme.SEPIA) },
                        modifier = Modifier.weight(1f)
                    )
                    ThemeOptionButton(
                        label = "रात्रि",
                        isSelected = currentTheme == ReadingTheme.DARK,
                        bgColor = DarkBg,
                        textColor = DarkText,
                        onClick = { prefsManager.setTheme(ReadingTheme.DARK) },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Font Size Adjuster
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("अक्षर आकार (Font Size):", style = MaterialTheme.typography.labelMedium, color = textMutedColor)
                    Text("${fontSizeSp.toInt()} sp", fontWeight = FontWeight.Bold, color = GoldPrimary)
                }
                Slider(
                    value = fontSizeSp,
                    onValueChange = { prefsManager.setFontSize(it) },
                    valueRange = 14f..25f,
                    steps = 10,
                    colors = SliderDefaults.colors(
                        thumbColor = GoldPrimary,
                        activeTrackColor = GoldPrimary
                    )
                )

                // Font Type Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (useSerif) "शैली: मार्तेल (Martel Serif)" else "शैली: मुक्ता (Mukta Sans)",
                        style = MaterialTheme.typography.labelMedium,
                        color = textColor
                    )
                    Switch(
                        checked = useSerif,
                        onCheckedChange = { prefsManager.setUseSerif(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = GoldPrimary,
                            checkedTrackColor = GoldPrimary.copy(alpha = 0.5f)
                        )
                    )
                }

                Button(
                    onClick = { showSettingsSheet = false },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                ) {
                    Text("सम्पन्न (Done)", color = DarkBg, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    surfaceColor: Color,
    borderColor: Color,
    textColor: Color,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontFamily = MartelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = textColor
                )
            }
            content()
        }
    }
}

@Composable
fun ThemeOptionButton(
    label: String,
    isSelected: Boolean,
    bgColor: Color,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        color = bgColor,
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, if (isSelected) GoldPrimary else Color.Gray.copy(alpha = 0.3f)),
        modifier = modifier.height(44.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                color = textColor,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontSize = 13.sp
            )
        }
    }
}

data class Tuple5<A, B, C, D, E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
)
