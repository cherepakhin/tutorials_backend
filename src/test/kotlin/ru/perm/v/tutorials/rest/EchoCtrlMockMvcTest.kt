package ru.perm.v.tutorials.rest

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(EchoCtrl::class)
class EchoCtrlMockMvcTest {
    @Autowired
    lateinit private var mockMvc: MockMvc

    @Test
    fun testProcessCreationFormSuccess() {
        val mes = mockMvc.perform(get("/echo/ECHO_MESSAGE"))
            .andExpect(status().isOk)
            .andReturn()

        assertEquals("ECHO_MESSAGE", mes.response.contentAsString)
    }
}