package ru.perm.v.tutorials.rest

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class LogCtrlTest {

    @Test
    fun getLog() {
        val ctrl = LogCtrl()

        assertTrue { ctrl.getLog().length > 0 }
    }
}