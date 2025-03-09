package ru.perm.v.tutorials.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

internal class TutorialEntityTest {
    @Test
    fun create() {
        val N = 100L
        val NAME = "NAME"
        val DESCRIPTION: String = "DESCRIPTION"
        var PUBLISHED: Boolean = false
        var SUBMITTED: Boolean = false

        val entity = TutorialEntity(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        assertEquals(100L, entity.n)
        assertEquals("NAME", entity.name)
        assertEquals("DESCRIPTION", entity.description)
        assertEquals(PUBLISHED, entity.published)
        assertEquals(SUBMITTED, entity.submitted)
    }

    @Test
    fun equals() {
        val N = 100L
        val NAME = "NAME"
        val DESCRIPTION = "DESCRIPTION"
        var PUBLISHED = false
        var SUBMITTED = false

        val entity1 = TutorialEntity(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        val entity2 = TutorialEntity(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        assertEquals(entity1, entity2)
        // Равенство структур == - проверка через equals()
        // Равенство ссылок === - две ссылки указывают на один и тот же объект
        assert(entity1.hashCode() == entity2.hashCode())
    }

    @Test
    fun notEqualsByN() {
        val NAME = "NAME"
        val DESCRIPTION: String = "DESCRIPTION"
        var PUBLISHED: Boolean = false
        var SUBMITTED: Boolean = false

        val entity1 = TutorialEntity(100L, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        val entity2 = TutorialEntity(200L, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        assertNotEquals(entity1, entity2)
    }

    @Test
    fun notEqualsByName() {
        val N = 100L
        val NAME1 = "NAME1"
        val NAME2 = "NAME2"
        val DESCRIPTION: String = "DESCRIPTION"
        var PUBLISHED: Boolean = false
        var SUBMITTED: Boolean = false

        val entity1 = TutorialEntity(N, NAME1, DESCRIPTION, PUBLISHED, SUBMITTED)
        val entity2 = TutorialEntity(N, NAME2, DESCRIPTION, PUBLISHED, SUBMITTED)

        assertNotEquals(entity1, entity2)
    }

    @Test
    fun hashCodeTest() {
        val N = 100L
        val NAME = "NAME"
        val DESCRIPTION: String = "DESCRIPTION"
        var PUBLISHED: Boolean = false
        var SUBMITTED: Boolean = false

        val entity1 = TutorialEntity(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        val entity2 = TutorialEntity(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        assertEquals(entity1.hashCode(), entity2.hashCode())
        assert(entity1.hashCode() == entity2.hashCode())
    }
}