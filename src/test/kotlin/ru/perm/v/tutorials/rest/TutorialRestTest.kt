package ru.perm.v.tutorials.rest

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import org.springframework.mock.web.MockHttpServletRequest
import ru.perm.v.tutorials.dto.TutorialDTO
import ru.perm.v.tutorials.service.TutorialService
import javax.servlet.http.HttpServletRequest

@ExtendWith(MockitoExtension::class)
internal class TutorialRestYaGPT5PRO {

    @Mock
    private lateinit var mockTutorialService: TutorialService

    @InjectMocks
    private lateinit var tutorialRest: TutorialRest

    @Test
    fun getByN() {
        val N = 1L
        val NAME = "NAME_1"
        val DESCRIPTION: String = "DESCRIPTION_1"
        val PUBLISHED: Boolean = false
        val SUBMITTED: Boolean = false

        val tutorialDTO = TutorialDTO(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        Mockito.`when`(mockTutorialService.getByN(N))
            .thenReturn(tutorialDTO)
        val dto = tutorialRest.getByN(N)

        assertEquals(TutorialDTO(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED), dto)
    }

    @Test
    fun getAll() {
        val N1 = 1L
        val N2 = 2L
        val NAME = "NAME_1"
        val DESCRIPTION: String = "DESCRIPTION_1"
        val PUBLISHED: Boolean = false
        val SUBMITTED: Boolean = false

        val dto1 = TutorialDTO(N1, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        val dto2 = TutorialDTO(N2, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        Mockito.`when`(mockTutorialService.getAll())
            .thenReturn(listOf(dto1, dto2))

        val dtos = tutorialRest.getAll()

        assertEquals(2, dtos.size)
        assertEquals(dto1, dtos.get(0))
        assertEquals(dto2, dtos.get(1))
    }

    @Test
    fun validateDTO() {
        val N1 = 1L
        val NAME_EMPTY = ""
        val DESCRIPTION: String = "DESCRIPTION_1"
        val PUBLISHED: Boolean = false
        val SUBMITTED: Boolean = false

        val dto = TutorialDTO(N1, NAME_EMPTY, DESCRIPTION, PUBLISHED, SUBMITTED)
        var exceptMessage = ""
        try {
            tutorialRest.validate(dto)
        } catch (excp: Exception) {
            exceptMessage = excp.message!!
        }

        assertEquals(
            "TutorialDTO(n=1, name=, description=DESCRIPTION_1, published=false, submitted=false) has errors: Field 'name' in TutorialDTO is empty\n",
            exceptMessage
        )
    }

    @Test
    fun create() {
        val N1 = 1L
        val NAME = "NAME_1"
        val DESCRIPTION: String = "DESCRIPTION_1"
        val PUBLISHED: Boolean = false
        val SUBMITTED: Boolean = false

        val dto1 = TutorialDTO(N1, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        val createdDTO = TutorialDTO(N1, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        Mockito.`when`(mockTutorialService.create(dto1))
            .thenReturn(createdDTO)

        val dto = tutorialRest.create(dto1)

        assertEquals(createdDTO, dto)
    }

    @Test
    fun createWhenNotValidTutorialDTO() {
        val N1 = 1L
        val NAME = ""
        val DESCRIPTION: String = "DESCRIPTION"
        val PUBLISHED: Boolean = false
        val SUBMITTED: Boolean = false

        val tutorial = TutorialDTO(N1, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        val exception = assertThrows<Exception> { tutorialRest.create(tutorial) }

        assertEquals(
            "TutorialDTO(n=1, name=, description=DESCRIPTION, published=false, submitted=false) has errors: Field 'name' in TutorialDTO is empty\n",
            exception.message
        )
    }

    @Test
    fun updateWhenNotValidTutorialDTO() {
        val N1 = 1L
        val NAME = ""
        val DESCRIPTION: String = "DESCRIPTION"
        val PUBLISHED: Boolean = false
        val SUBMITTED: Boolean = false

        val tutorial = TutorialDTO(N1, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        val exception = assertThrows<Exception> { tutorialRest.update(N1, tutorial) }

        assertEquals(
            "TutorialDTO(n=1, name=, description=DESCRIPTION, published=false, submitted=false) has errors: Field 'name' in TutorialDTO is empty\n",
            exception.message
        )

        Mockito.verify(mockTutorialService, never()).update(any())
    }

    @Test
    fun updateWhenValidTutorialDTODoesNotThrow() {
        val N1 = 1L
        val NAME = "NAME_1"
        val DESCRIPTION: String = ""
        val PUBLISHED: Boolean = false
        val SUBMITTED: Boolean = false

        val dto = TutorialDTO(N1, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        assertDoesNotThrow { tutorialRest.update(N1, dto) }
    }

    @Test
    fun update() {
        val N1 = 1L
        val UPDATED_NAME = "UPDATED_NAME_1"
        val DESCRIPTION: String = ""
        val PUBLISHED: Boolean = false
        val SUBMITTED: Boolean = false

        val dto = TutorialDTO(N1, UPDATED_NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        val updatedDto = TutorialDTO(N1, UPDATED_NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        Mockito.`when`(mockTutorialService.update(dto))
            .thenReturn(updatedDto)

        val returnedDTO = tutorialRest.update(N1, dto)

        assertEquals(updatedDto, returnedDTO)
    }

    @Test
    fun deleteById() {
        val N = 100L

        tutorialRest.deleteById(N)

        verify(mockTutorialService, times(1)).delete(N)
    }

    @Test
    fun deleteByNotExistId() {
        val ID = 100L
        doThrow(Exception("ERROR_MESSAGE")).`when`(mockTutorialService).delete(ID)

        val exception = assertThrows<Exception> { tutorialRest.deleteById(ID) }

        assertEquals(
            "ERROR_MESSAGE",
            exception.message
        )
    }

    @Test
    fun byParameter() {
        val N1 = 1L
        val N2 = 2L
        val NAME = "NAME_1"
        val DESCRIPTION: String = "DESCRIPTION_1"
        val PUBLISHED: Boolean = false
        val SUBMITTED: Boolean = false
        val dto1 = TutorialDTO(N1, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        val dto2 = TutorialDTO(N2, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        Mockito.`when`(mockTutorialService.findByNameContainingOrderByName("someName"))
            .thenReturn(listOf(dto1, dto2))


        val request = MockHttpServletRequest()
        request.addParameter("name", "someName");


        val foundTutorials =  tutorialRest.byParameter(request)

        assertEquals(2, foundTutorials.size)
    }

}