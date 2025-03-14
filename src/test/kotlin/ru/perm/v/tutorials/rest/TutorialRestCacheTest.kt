package ru.perm.v.tutorials.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.perm.v.tutorials.dto.TutorialDTO
import ru.perm.v.tutorials.service.TutorialService
import kotlin.test.assertEquals

@ExtendWith(SpringExtension::class)
@WebMvcTest(TutorialRest::class)
@ContextConfiguration
class TutorialRestCacheTest(
    @Autowired val tutorialRest: TutorialRest,
    @Autowired private val mockMvc: MockMvc,
) {
    @MockBean
    lateinit var tutorialService: TutorialService

    @Autowired
    lateinit var cache: CacheManager

    val mapper = ObjectMapper().registerModule(KotlinModule())

    @EnableCaching
    @TestConfiguration
    class CachingTestConfig {
        @Bean
        fun cacheManager(): CacheManager = ConcurrentMapCacheManager("tutorials")
    }

    @Test
    fun call3timesGetByN() {
        val N = 100L

        val tutorial = TutorialDTO(N, "NAME", "DESCRIPTION", false, false)
        Mockito.`when`(tutorialService.getByN(N)).thenReturn(tutorial)
        val REQUEST_URL = "/tutorial/$N"
        // call 3 times
        mockMvc.perform(
            MockMvcRequestBuilders.get(REQUEST_URL)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()
        mockMvc.perform(
            MockMvcRequestBuilders.get(REQUEST_URL)
        )
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get(REQUEST_URL)
        ).andReturn()

        // but tutorialService.getByN(N) was called only 1 time
        verify(tutorialService, times(1)).getByN(N)

        val receivedProductDTO = mapper.readValue<TutorialDTO>(result.response.contentAsString)
        assertEquals(tutorial, receivedProductDTO)
    }

    @Test
    fun call3timesGetAll() {
        val N_111 = 111L
        val N_222 = 222L
        val NAME = "NAME"
        val DESCRIPTION = ""
        val PUBLISHED = false
        val SUBMITTED = false

        val tutorial1 = TutorialDTO(N_111, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        val tutorial2 = TutorialDTO(N_222, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)

        Mockito.`when`(tutorialService.getAll()).thenReturn(listOf(tutorial1, tutorial2))

        tutorialRest.getAll()
        tutorialRest.getAll()
        val tutorials = tutorialRest.getAll()

        assertEquals(2, tutorials.size)
        verify(tutorialService, times(1)).getAll()
    }

    @Test
    fun doGet_then_doUpdate_and_doGet_for_CheckEvict() {
        val N = 10L
        val NAME = "NAME"
        val DESCRIPTION = "DESCRIPTION"
        val PUBLISHED = false
        val SUBMITTED = false

        Mockito.`when`(tutorialService.getByN(N)).thenReturn(TutorialDTO(
            N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED))

        val tutorialDTO_10 = TutorialDTO(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        doReturn(tutorialDTO_10).`when`(tutorialService).update(tutorialDTO_10)

        val REQUEST_URL = "/tutorial/$N"
        // GET
        val result = mockMvc.perform(
            MockMvcRequestBuilders
                .get(REQUEST_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(tutorialDTO_10))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()


        //Update
        mockMvc.perform(
            MockMvcRequestBuilders
                .post(REQUEST_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(tutorialDTO_10))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        // GET
        val lastTutorialJson = mockMvc.perform(
            MockMvcRequestBuilders
                .get(REQUEST_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(tutorialDTO_10))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn().response.contentAsString

        verify(tutorialService, times(2)).getByN(N) // cacheEVICT was called. GET REQUEST was called not 1 time, but 2 times

        val receivedProductDTO = mapper.readValue<TutorialDTO>(lastTutorialJson)
        assertEquals(tutorialDTO_10, receivedProductDTO)
    }

}