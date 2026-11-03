package com.example

import com.example.data.HistoryRepository
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testBookStructureCompleteness() {
        val parts = HistoryRepository.allParts
        assertEquals("Book must have exactly 10 parts", 10, parts.size)

        val allChapters = HistoryRepository.getAllChapters()
        assertEquals("Book must have exactly 78 chapters", 78, allChapters.size)

        // Verify Part IV (History of Nepal)
        val nepalPart = parts.find { it.partNumber == 4 }
        assertNotNull("Part IV (Nepal) must be present", nepalPart)
        assertEquals(9, nepalPart!!.chapters.size)
        assertTrue(nepalPart.titleDevanagari.contains("नेपाल"))

        // Verify Chapter 24 (Ancient Nepal)
        val ch24 = HistoryRepository.getChapterById(24)
        assertNotNull(ch24)
        assertTrue(ch24!!.titleDevanagari.contains("प्राचीन नेपाल"))
        assertTrue(ch24.subheadings.isNotEmpty())

        // Verify Chapter 29 (Prithvi Narayan Shah)
        val ch29 = HistoryRepository.getChapterById(29)
        assertNotNull(ch29)
        assertTrue(ch29!!.titleDevanagari.contains("पृथ्वीनारायण शाह"))

        // Verify Quiz Questions
        assertTrue(HistoryRepository.quizQuestions.isNotEmpty())
        assertTrue(HistoryRepository.masterTimeline.isNotEmpty())
    }
}
