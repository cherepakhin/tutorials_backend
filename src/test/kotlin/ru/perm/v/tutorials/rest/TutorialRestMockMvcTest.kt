package ru.perm.v.tutorials.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.perm.v.tutorials.dto.TutorialDTO
import ru.perm.v.tutorials.service.TutorialService

@ExtendWith(SpringExtension::class)
@WebMvcTest(TutorialRest::class)
class TutorialRestMockMvcTest(@Autowired private val mockMvc: MockMvc) {

    // Used "@MockBean" for REMOVE ERROR for MockMvc
    // "... available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: ..."
    // @MockBean is mock for SPRING BEAN! Must be added @ExtendWith(SpringExtension::class @WebMvcTest(ProductRest::class))
    // else get error "... available: expected at least 1 bean"
    @MockBean
    private lateinit var mockTutorialService: TutorialService

    val mapper = ObjectMapper().registerModule(KotlinModule())

    @Test
    fun echoMessage() {
        val mes = mockMvc.perform(
            MockMvcRequestBuilders.get("/tutorial/echo/ECHO_MESSAGE")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        assertEquals("ECHO_MESSAGE", mes.response.contentAsString)
    }

    @Test
    fun create() {
        val N = 1L
        val NAME = "NAME_1"
        val DESCRIPTION: String = "DESCRIPTION_1"
        val PUBLISHED: Boolean = false
        val SUBMITTED: Boolean = false
        val tutorialDTO = TutorialDTO(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        doReturn(tutorialDTO).`when`(mockTutorialService).create(tutorialDTO)

        val message = mockMvc.perform(
            MockMvcRequestBuilders
                .post("/tutorial")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(tutorialDTO))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()
        val messageAsString = message.response.contentAsString;

        assertEquals("{\"n\":10,\"name\":\"NAME_10\",\"groupDtoN\":-1}", messageAsString)

        val receivedDTO = mapper.readValue<TutorialDTO>(messageAsString)
        assertEquals(tutorialDTO, receivedDTO)
    }

    @Test
    fun getByN() {
        val N = 1L
        val NAME = "NAME_1"
        val DESCRIPTION: String = "DESCRIPTION_1"
        val PUBLISHED: Boolean = false
        val SUBMITTED: Boolean = false
        val tutorialDTO = TutorialDTO(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        doReturn(tutorialDTO).`when`(mockTutorialService).getByN(N)

        val mes = mockMvc.perform(
            MockMvcRequestBuilders
                .get("/tutorial/" + N)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(tutorialDTO))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        assertEquals("{\"n\":1,\"name\":\"NAME_1\",\"description\":\"DESCRIPTION_1\",\"published\":false,\"submitted\":false}", mes.response.contentAsString)

        val receivedProductDTO = mapper.readValue<TutorialDTO>(mes.response.contentAsString)
        assertEquals(tutorialDTO, receivedProductDTO)
    }

    @Test
    fun getAll() {
        val NAME = "NAME_1"
        val DESCRIPTION: String = "DESCRIPTION_1"
        val PUBLISHED: Boolean = false
        val SUBMITTED: Boolean = false
        val N10 = 10L
        val N11 = 11L

        val tutorialDTO_10 = TutorialDTO(N10, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        val tutorialDTO_11 = TutorialDTO(N11, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        val tutorials = listOf<TutorialDTO>(tutorialDTO_10, tutorialDTO_11)
        doReturn(tutorials).`when`(mockTutorialService).getAll()

        val mes = mockMvc.perform(
            MockMvcRequestBuilders
                .get("/tutorial/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(tutorials))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val receivedDTOs = mapper.readValue<List<TutorialDTO>>(mes.response.contentAsString)

        assertEquals(2, receivedDTOs.size)
        assertEquals(tutorialDTO_10, receivedDTOs.get(0))
        assertEquals(tutorialDTO_11, receivedDTOs.get(1))
    }

    @Test
    fun update() {
        val N10 = 10L
        val NAME = "NAME_1"
        val DESCRIPTION = "DESCRIPTION_1"
        val PUBLISHED = false
        val SUBMITTED = false

        val tutorialDTO_10 = TutorialDTO(N10, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        doReturn(tutorialDTO_10).`when`(mockTutorialService).update(tutorialDTO_10)

        val mes = mockMvc.perform(
            MockMvcRequestBuilders
                .post("/tutorial/" + N10)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(tutorialDTO_10))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        assertEquals("{\"n\":10,\"name\":\"NAME_1\",\"description\":\"DESCRIPTION_1\",\"published\":false,\"submitted\":false}", mes.response.contentAsString)

        val receivedProductDTO = mapper.readValue<TutorialDTO>(mes.response.contentAsString)
        assertEquals(tutorialDTO_10, receivedProductDTO)
    }

    @Test
    fun deleteById() {
        val N = 10L

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/tutorial/" + N)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        verify(mockTutorialService, times(1)).delete(N)
    }

    @Test
    fun deleteByNotExistId() {
        val N = 10L

        `when`(mockTutorialService.delete(N)).thenThrow(Exception("ERROR"))

        assertThrows<Exception> {
            mockMvc.perform(
                MockMvcRequestBuilders
                    .delete("/tutorial/" + N)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andReturn()
        }
    }

    @Test
    fun getByExistId_generatedIdeaGPT() {
        val N10 = 10L
        val NAME = "NAME_1"
        val DESCRIPTION: String = "DESCRIPTION_1"
        var PUBLISHED: Boolean = false
        var SUBMITTED: Boolean = false

        val tutorialDTO_10 = TutorialDTO(N10, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        `when`(mockTutorialService.getByN(N10)).thenReturn(tutorialDTO_10)

        val message = mockMvc.perform(
            MockMvcRequestBuilders.get("/product/" + N10)
        )
            .andExpect(status().isOk)
            .andReturn()
            .response.contentAsString

        assertEquals("{\"n\":10,\"name\":\"NAME_10\",\"groupDtoN\":-1}", message)
    }
}