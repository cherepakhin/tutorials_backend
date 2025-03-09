package ru.perm.v.tutorials.rest

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import ru.perm.v.tutorials.dto.TutorialDTO
import ru.perm.v.tutorials.service.TutorialService
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TutorialRestCacheTest(@Autowired val tutorialRest: TutorialRest) {
    @MockBean
    lateinit var tutorialService: TutorialService

    @Test
    fun getByN() {
        val N = 100L
        val NAME = "NAME"
        val DESCRIPTION: String = ""
        var PUBLISHED: Boolean = false
        var SUBMITTED: Boolean = false

        val tutorial = TutorialDTO(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        Mockito.`when`(tutorialService.getByN(N)).thenReturn(tutorial)
        // call 3 times
        tutorialRest.getByN(N)
        tutorialRest.getByN(N)
        val dto = tutorialRest.getByN(N)

        assertEquals(N, dto.n)
        // but productService.getByN(N) was called only 1 time
        verify(tutorialService, times(1)).getByN(N)
    }

    @Test
    fun getByN_WithDelete() {
        val N = 100L
        val NAME = "NAME"
        val DESCRIPTION: String = ""
        var PUBLISHED: Boolean = false
        var SUBMITTED: Boolean = false

        val tutorial = TutorialDTO(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        Mockito.`when`(tutorialService.getByN(N)).thenReturn(tutorial)
        // call 3 times
        tutorialRest.getByN(N)
        tutorialRest.getByN(N)
        val dto = tutorialRest.getByN(N)

        assertEquals(N, dto.n)
        // but productService.getByN(N) was called only 1 time
        verify(tutorialService, times(1)).getByN(N)

        tutorialRest.deleteById(N)
    }

    @Test
    fun getAll() {
        val N_111 = 111L
        val N_222 = 222L
        val NAME = "NAME"
        val DESCRIPTION: String = ""
        var PUBLISHED: Boolean = false
        var SUBMITTED: Boolean = false

        val tutorial1 = TutorialDTO(N_111, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        val tutorial2 = TutorialDTO(N_222, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        Mockito.`when`(tutorialService.getAll()).thenReturn(listOf(tutorial1, tutorial2))

        tutorialRest.getAll()
        tutorialRest.getAll()
        val tutorials = tutorialRest.getAll()

        assertEquals(2, tutorials.size)
        verify(tutorialService, times(1)).getAll()
    }
}