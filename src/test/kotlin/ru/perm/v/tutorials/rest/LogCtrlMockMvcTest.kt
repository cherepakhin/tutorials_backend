package ru.perm.v.tutorials.rest

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.test.assertTrue

@ExtendWith(SpringExtension::class)
@WebMvcTest(LogCtrl::class)
class LogCtrlMockMvcTest(@Autowired private val mockMvc: MockMvc) {
    @Test
    fun getLog() {
        val mes = mockMvc.perform(
            MockMvcRequestBuilders.get("/log/")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        assertTrue { mes.response.contentAsString.length > 0 }
    }
}