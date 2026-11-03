package com.example.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("grand_saga_history_prefs", Context.MODE_PRIVATE)

    private val _bookmarkedChapterIds = MutableStateFlow(loadBookmarks())
    val bookmarkedChapterIds: StateFlow<Set<Int>> = _bookmarkedChapterIds.asStateFlow()

    private val _lastReadChapterId = MutableStateFlow(prefs.getInt(KEY_LAST_READ_CHAPTER, 1))
    val lastReadChapterId: StateFlow<Int> = _lastReadChapterId.asStateFlow()

    private val _fontSizeSp = MutableStateFlow(prefs.getFloat(KEY_FONT_SIZE, 17f))
    val fontSizeSp: StateFlow<Float> = _fontSizeSp.asStateFlow()

    private val _theme = MutableStateFlow(
        ReadingTheme.valueOf(prefs.getString(KEY_THEME, ReadingTheme.PARCHMENT.name) ?: ReadingTheme.PARCHMENT.name)
    )
    val theme: StateFlow<ReadingTheme> = _theme.asStateFlow()

    private val _useSerif = MutableStateFlow(prefs.getBoolean(KEY_SERIF, true))
    val useSerif: StateFlow<Boolean> = _useSerif.asStateFlow()

    private fun loadBookmarks(): Set<Int> {
        val stringSet = prefs.getStringSet(KEY_BOOKMARKS, emptySet()) ?: emptySet()
        return stringSet.mapNotNull { it.toIntOrNull() }.toSet()
    }

    fun toggleBookmark(chapterId: Int) {
        val current = _bookmarkedChapterIds.value.toMutableSet()
        if (current.contains(chapterId)) {
            current.remove(chapterId)
        } else {
            current.add(chapterId)
        }
        prefs.edit().putStringSet(KEY_BOOKMARKS, current.map { it.toString() }.toSet()).apply()
        _bookmarkedChapterIds.value = current
    }

    fun setLastReadChapter(chapterId: Int) {
        prefs.edit().putInt(KEY_LAST_READ_CHAPTER, chapterId).apply()
        _lastReadChapterId.value = chapterId
    }

    fun setFontSize(sizeSp: Float) {
        val clamped = sizeSp.coerceIn(13f, 26f)
        prefs.edit().putFloat(KEY_FONT_SIZE, clamped).apply()
        _fontSizeSp.value = clamped
    }

    fun setTheme(theme: ReadingTheme) {
        prefs.edit().putString(KEY_THEME, theme.name).apply()
        _theme.value = theme
    }

    fun setUseSerif(useSerif: Boolean) {
        prefs.edit().putBoolean(KEY_SERIF, useSerif).apply()
        _useSerif.value = useSerif
    }

    companion object {
        private const val KEY_BOOKMARKS = "key_bookmarks"
        private const val KEY_LAST_READ_CHAPTER = "key_last_read_chapter"
        private const val KEY_FONT_SIZE = "key_font_size"
        private const val KEY_THEME = "key_theme"
        private const val KEY_SERIF = "key_use_serif"
    }
}
