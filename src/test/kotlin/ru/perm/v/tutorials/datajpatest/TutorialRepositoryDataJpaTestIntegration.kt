package ru.perm.v.tutorials.datajpatest

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ru.perm.v.tutorials.repository.TutorialRepository
import kotlin.test.assertEquals

/**
 * Еще один вариант интеграционных тестов.
 * Тестируется работа ТОЛЬКО с базой данных, И ТОЛЬКО TutorialRepository
 * с использованием @DataJpaTest.
 */
@DataJpaTest
class TutorialRepositoryDataJpaTestIntegration {
    @Autowired
    lateinit var tutorialRepository: TutorialRepository

    @Test
    fun getAll() {
        val prices = tutorialRepository.findAll()

        assertEquals(4, prices.size);
    }

    @Test
    fun getByDslFilterFromRepository() {
        assertEquals(4, tutorialRepository.findAllByOrderByNAsc().size)
    }
}