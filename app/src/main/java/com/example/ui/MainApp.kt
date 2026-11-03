package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.HistoryRepository
import com.example.data.PreferencesManager
import com.example.ui.theme.GoldPrimary

enum class AppDestination {
    HOME,
    TOC,
    READER,
    TIMELINE,
    QUIZ,
    BOOKMARKS,
    INTRODUCTION
}

@Composable
fun MainApp(
    prefsManager: PreferencesManager,
    modifier: Modifier = Modifier
) {
    var currentDestination by remember { mutableStateOf(AppDestination.HOME) }
    var activeChapterId by remember { mutableIntStateOf(1) }

    // BackHandler to handle custom state back navigation
    BackHandler(enabled = currentDestination != AppDestination.HOME) {
        if (currentDestination == AppDestination.READER) {
            currentDestination = AppDestination.TOC
        } else {
            currentDestination = AppDestination.HOME
        }
    }

    Scaffold(
        bottomBar = {
            // Only show bottom bar when not in Reader or Intro mode
            if (currentDestination != AppDestination.READER && currentDestination != AppDestination.INTRODUCTION) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp,
                    modifier = Modifier.navigationBarsPadding()
                ) {
                    NavigationBarItem(
                        selected = currentDestination == AppDestination.HOME,
                        onClick = { currentDestination = AppDestination.HOME },
                        icon = {
                            Icon(
                                if (currentDestination == AppDestination.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                                contentDescription = "गृहपृष्ठ"
                            )
                        },
                        label = { Text("गृहपृष्ठ") },
                        modifier = Modifier.testTag("nav_home"),
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = GoldPrimary,
                            indicatorColor = GoldPrimary
                        )
                    )

                    NavigationBarItem(
                        selected = currentDestination == AppDestination.TOC,
                        onClick = { currentDestination = AppDestination.TOC },
                        icon = {
                            Icon(
                                if (currentDestination == AppDestination.TOC) Icons.Filled.FormatListNumbered else Icons.Outlined.FormatListNumbered,
                                contentDescription = "विषयसूची"
                            )
                        },
                        label = { Text("विषयसूची") },
                        modifier = Modifier.testTag("nav_toc"),
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = GoldPrimary,
                            indicatorColor = GoldPrimary
                        )
                    )

                    NavigationBarItem(
                        selected = currentDestination == AppDestination.TIMELINE,
                        onClick = { currentDestination = AppDestination.TIMELINE },
                        icon = {
                            Icon(
                                if (currentDestination == AppDestination.TIMELINE) Icons.Filled.Timeline else Icons.Outlined.Timeline,
                                contentDescription = "समयरेखा"
                            )
                        },
                        label = { Text("समयरेखा") },
                        modifier = Modifier.testTag("nav_timeline"),
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = GoldPrimary,
                            indicatorColor = GoldPrimary
                        )
                    )

                    NavigationBarItem(
                        selected = currentDestination == AppDestination.QUIZ,
                        onClick = { currentDestination = AppDestination.QUIZ },
                        icon = {
                            Icon(
                                if (currentDestination == AppDestination.QUIZ) Icons.Filled.Quiz else Icons.Outlined.Quiz,
                                contentDescription = "क्विज"
                            )
                        },
                        label = { Text("क्विज") },
                        modifier = Modifier.testTag("nav_quiz"),
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = GoldPrimary,
                            indicatorColor = GoldPrimary
                        )
                    )

                    NavigationBarItem(
                        selected = currentDestination == AppDestination.BOOKMARKS,
                        onClick = { currentDestination = AppDestination.BOOKMARKS },
                        icon = {
                            Icon(
                                if (currentDestination == AppDestination.BOOKMARKS) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                contentDescription = "सुरक्षित"
                            )
                        },
                        label = { Text("सुरक्षित") },
                        modifier = Modifier.testTag("nav_bookmarks"),
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = GoldPrimary,
                            indicatorColor = GoldPrimary
                        )
                    )
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            when (currentDestination) {
                AppDestination.HOME -> {
                    HomeScreen(
                        prefsManager = prefsManager,
                        onNavigateToChapter = { id ->
                            activeChapterId = id
                            currentDestination = AppDestination.READER
                        },
                        onNavigateToToc = { currentDestination = AppDestination.TOC },
                        onNavigateToTimeline = { currentDestination = AppDestination.TIMELINE },
                        onNavigateToQuiz = { currentDestination = AppDestination.QUIZ },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                AppDestination.TOC -> {
                    TableOfContentsScreen(
                        onSelectChapter = { id ->
                            activeChapterId = id
                            currentDestination = AppDestination.READER
                        },
                        onOpenIntroduction = { currentDestination = AppDestination.INTRODUCTION },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                AppDestination.READER -> {
                    ChapterReaderScreen(
                        chapterId = activeChapterId,
                        prefsManager = prefsManager,
                        onNavigateBack = { currentDestination = AppDestination.TOC },
                        onNavigateToChapter = { id -> activeChapterId = id },
                        modifier = Modifier
                    )
                }

                AppDestination.TIMELINE -> {
                    TimelineScreen(
                        onNavigateToChapter = { id ->
                            activeChapterId = id
                            currentDestination = AppDestination.READER
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                AppDestination.QUIZ -> {
                    QuizScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                AppDestination.BOOKMARKS -> {
                    BookmarksScreen(
                        prefsManager = prefsManager,
                        onNavigateToChapter = { id ->
                            activeChapterId = id
                            currentDestination = AppDestination.READER
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                AppDestination.INTRODUCTION -> {
                    IntroductionScreen(
                        onBack = { currentDestination = AppDestination.TOC },
                        onStartReading = {
                            activeChapterId = 1
                            currentDestination = AppDestination.READER
                        }
                    )
                }
            }
        }
    }
}
