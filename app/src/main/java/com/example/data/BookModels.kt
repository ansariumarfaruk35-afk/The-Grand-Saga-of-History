package com.example.data

enum class ReadingTheme {
    PARCHMENT, // Classic warm manuscript
    SEPIA,     // Aged golden sepia
    DARK       // Royal night obsidian
}

data class BookSection(
    val title: String,
    val content: String,
    val iconType: String = "info"
)

data class TimelineEvent(
    val year: String,
    val title: String,
    val description: String,
    val era: String = ""
)

data class Personality(
    val name: String,
    val role: String,
    val description: String,
    val famousQuote: String = ""
)

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class Chapter(
    val id: Int,
    val partNumber: Int,
    val chapterNumber: Int,
    val titleDevanagari: String,
    val titleEnglish: String,
    val timePeriod: String,
    val subheadings: List<String>,
    val keyQuote: String,
    // The 12 rigorous sections specified in the Master Prompt:
    val historicalBackground: String,
    val detailedExplanation: String,
    val importantPersonalities: List<Personality>,
    val timelineEvents: List<TimelineEvent>,
    val causesAndFactors: List<String>,
    val consequencesAndImpact: List<String>,
    val interestingFacts: List<String>,
    val historiansPerspectives: String,
    val modernWorldConnection: String,
    val lessonsForHumanity: List<String>,
    val powerfulConclusion: String
)

data class BookPart(
    val partNumber: Int,
    val titleDevanagari: String,
    val titleEnglish: String,
    val timePeriod: String,
    val description: String,
    val chapters: List<Chapter>
)

data class ReadingSettings(
    val fontSizeSp: Float = 17f,
    val theme: ReadingTheme = ReadingTheme.PARCHMENT,
    val useSerifFont: Boolean = true,
    val isTtsPlaying: Boolean = false
)
