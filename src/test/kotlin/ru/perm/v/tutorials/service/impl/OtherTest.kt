package ru.perm.v.tutorials.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OtherTest {
    @Test
    fun joinToString_FromList() {
        val listSortBy: List<String> = listOf("name", "n")

        assertEquals("name,n", listSortBy.joinToString(","))
    }

    @Test
    fun fold_FromList() {
        val listSortBy: List<String> = listOf("name", "n")
        val sort = listSortBy.fold("_") { acc, next -> "$acc,$next" }

        assertEquals("_,name,n", sort)
    }
}