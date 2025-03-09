package ru.perm.v.tutorials.rest

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class EchoCtrlTest {

    @Test
    fun echoStr() {
        val ctrl = EchoCtrl()

        assertEquals("MES", ctrl.echoStr("MES"))
    }
}