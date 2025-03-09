package ru.perm.v.tutorials.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

internal class TutorialDTOTest {
    @Test
    fun create() {
        val N = 100L
        val NAME = "NAME"
        val DESCRIPTION: String = ""
        var PUBLISHED: Boolean = false
        var SUBMITTED: Boolean = false

        val dto = TutorialDTO(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        assertEquals(N, dto.n)
        assertEquals(NAME, dto.name)
        assertEquals(DESCRIPTION, dto.description)
        assertEquals(PUBLISHED, dto.published)
        assertEquals(SUBMITTED, dto.submitted)
    }

    @Test
    fun equals() {
        val N = 100L
        val NAME = "NAME"
        val DESCRIPTION: String = ""
        var PUBLISHED: Boolean = false
        var SUBMITTED: Boolean = false

        val dto1 = TutorialDTO(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        val dto2 = TutorialDTO(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        assertEquals(dto1, dto2) // as in java
    }

    @Test
    fun notEquals() {
        val N = 100L
        val NAME = "NAME"
        var PUBLISHED: Boolean = false
        var SUBMITTED: Boolean = false

        val dto1 = TutorialDTO(N, NAME, "DESCRIPTION_1", PUBLISHED, SUBMITTED)
        val dto2 = TutorialDTO(N, NAME, "DESCRIPTION_2", PUBLISHED, SUBMITTED)

        assertNotEquals(dto1, dto2) // as in java
    }

    @Test
    fun testHashCode() {
        val N = 100L
        val NAME = "NAME"
        val DESCRIPTION: String = ""
        var PUBLISHED: Boolean = false
        var SUBMITTED: Boolean = false

        val dto1 = TutorialDTO(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        val dto2 = TutorialDTO(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        assertEquals(dto1.hashCode(), dto2.hashCode())
    }
}