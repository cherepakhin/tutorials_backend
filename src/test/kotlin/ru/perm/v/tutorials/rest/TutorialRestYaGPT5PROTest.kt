package ru.perm.v.tutorials.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.perm.v.tutorials.dto.TutorialDTO
import ru.perm.v.tutorials.service.TutorialService

/**
 * Тест сгенерирован https://alice.yandex.ru/chat/0195483f-796a-409a-97d7-f8d782949fb9/
 * Напиши UNIT тесты к коду на Kotlin: дальше код TutorialRest.kt
 * Пара незначительных исправлений в виде кода
 * 1. .content(mapper.writeValueAsString(tutorialDTO))
 * 2. проверка статуса
 */
@ExtendWith(SpringExtension::class)
@WebMvcTest(TutorialRest::class)
class TutorialRestYaGPT5PROTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var tutorialService: TutorialService

    val mapper = ObjectMapper().registerModule(KotlinModule())

    @Test
    fun testEchoMessage() {
        val message = "test message"
        mockMvc.perform(get("/tutorial/echo/$message"))
            .andExpect(status().isOk)
            .andExpect(content().string(message))
    }

    @Test
    fun testCreateTutorial() {
        val N = 1L
        val NAME = "NAME_1"
        val DESCRIPTION: String = "DESCRIPTION_1"
        var PUBLISHED: Boolean = false
        var SUBMITTED: Boolean = false
        val tutorialDTO = TutorialDTO(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        `when`(tutorialService.create(tutorialDTO)).thenReturn(tutorialDTO)
        mockMvc.perform(
            post("/tutorial")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(tutorialDTO))
        )
            .andExpect(status().isOk)
    }

    @Test
    fun testGetByN() {
        val n = 1L
        val tutorialDTO = TutorialDTO()
        `when`(tutorialService.getByN(n)).thenReturn(tutorialDTO)
        mockMvc.perform(get("/tutorial/$n"))
            .andExpect(status().isOk)
    }

    @Test
    fun testGetAllTutorials() {
        val tutorials = listOf(TutorialDTO(), TutorialDTO())
        `when`(tutorialService.getAll()).thenReturn(tutorials)
        mockMvc.perform(get("/tutorial/"))
            .andExpect(status().isOk)
    }

    @Test
    fun testUpdateTutorial() {
        val N = 1L
        val NAME = "NAME_1"
        val DESCRIPTION: String = "DESCRIPTION_1"
        var PUBLISHED: Boolean = false
        var SUBMITTED: Boolean = false
        val tutorialDTO = TutorialDTO(N, NAME, DESCRIPTION, PUBLISHED, SUBMITTED)
        `when`(tutorialService.update(tutorialDTO)).thenReturn(tutorialDTO)
        mockMvc.perform(
            post("/tutorial/$N")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(tutorialDTO))
        )
            .andExpect(status().isOk)
    }

    @Test
    fun testDeleteById() {
        val n = 1L
        doNothing().`when`(tutorialService).delete(n)

        mockMvc.perform(delete("/tutorial/$n"))
            .andExpect(status().isOk)
    }
}
