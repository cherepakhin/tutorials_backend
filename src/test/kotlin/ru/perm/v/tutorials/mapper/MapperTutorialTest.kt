package ru.perm.v.tutorials.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.perm.v.tutorials.dto.TutorialDTO
import ru.perm.v.tutorials.entity.TutorialEntity

class MapperTutorialTest {

    @Test
    fun mapFromDtoToEntity() {
        val N = 1L
        val NAME = "NAME_1"
        val DESCRIPTION: String = "DESCRIPTION_1"
        var PUBLISHED: Boolean = false
        var SUBMITTED: Boolean = false
        val tutorialDTO = TutorialDTO(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        val entity = MapperTutorial.mapFromDtoToEntity(tutorialDTO)

        assertEquals(TutorialEntity(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED), entity)

    }

    @Test
    fun mapFromEntityToDto() {
        val N = 1L
        val NAME = "NAME_1"
        val DESCRIPTION: String = "DESCRIPTION_1"
        var PUBLISHED: Boolean = false
        var SUBMITTED: Boolean = false
        val tutorialEntity = TutorialEntity(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        val tutorialDTO = MapperTutorial.mapFromEntityToDto(tutorialEntity)

        assertEquals(TutorialDTO(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED), tutorialDTO)
    }
}