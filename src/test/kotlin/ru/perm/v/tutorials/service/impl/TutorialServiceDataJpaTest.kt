package ru.perm.v.tutorials.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ru.perm.v.tutorials.dto.TutorialDTO
import ru.perm.v.tutorials.filter.TutorialCriteria
import ru.perm.v.tutorials.repository.TutorialRepository
import kotlin.test.assertTrue

@DataJpaTest
class TutorialServiceDataJpaTest {
    @Autowired
    lateinit var tutorialRepository: TutorialRepository

    @Test
    fun forDEMO() {
        val tutorialService = TutorialServiceImpl(tutorialRepository)

        assertEquals(4, tutorialService.getAll().size)
    }

    @Test
    fun getByDslFilterByName() {
        val tutorialService = TutorialServiceImpl(tutorialRepository)
        val NAME = "Tutorial"

        val tutorials = tutorialService.findByNameContainingOrderByName(NAME)

        assertEquals(4, tutorials.size)
    }

    @Test
    fun findByNameContainingOrderByName() {
        val tutorialService = TutorialServiceImpl(tutorialRepository)
        val NAME = "Tutorial1"

        val tutorials = tutorialService.findByNameContainingOrderByName(NAME)

        assertEquals(1, tutorials.size)
        assertEquals(
            TutorialDTO(1, "Name Tutorial1", "Description Tutorial1", true, true),
            tutorials.get(0)
        )
    }

    @Test
    fun getCountOfTutorialNames() {
        val tutorialService = TutorialServiceImpl(tutorialRepository)

        val count = tutorialService.getCountOfTutorialNames()

        assertEquals(4, count)
    }

    @Test
    fun getNextN() {
        val tutorialService = TutorialServiceImpl(tutorialRepository)

        val nextN = tutorialService.getNextN()

        assertEquals(5, nextN)
    }

    @Test
    fun findByNameContainingOrderByNameAndDescriptionContaining() {
        val tutorialService = TutorialServiceImpl(tutorialRepository)

        val dtos = tutorialService
            .findByNameContainingAndDescriptionContainingOrderByName(
                "Name Tutorial1",
                "Description Tutorial1")

        assertEquals(1, dtos.size)
        assertEquals(
            TutorialDTO(
                1,
                "Name Tutorial1",
                "Description Tutorial1",
                true, true),
            dtos.get(0)
        )
    }


    @Test
    fun findByTutorialSpesificationEmpty() {
        val tutorialCriteria = TutorialCriteria()
        val tutorialService = TutorialServiceImpl(tutorialRepository)

        val tutors = tutorialService.findByTutorialCriteria(tutorialCriteria)

        assertTrue { tutors.size > 0 }
    }

    @Test
    fun findByTutorialSpesificationLikeAllName() {
        val tutorialCriteria = TutorialCriteria()
        tutorialCriteria.name = "%"
        val tutorialService = TutorialServiceImpl(tutorialRepository)

        val tutors = tutorialService.findByTutorialCriteria(tutorialCriteria)

        assertEquals(4, tutors.size)
    }

    @Test
    fun findByTutorialSpesificationLikeName2() {
        val tutorialCriteria = TutorialCriteria()
        tutorialCriteria.name = "2"
        val tutorialService = TutorialServiceImpl(tutorialRepository)

        val tutors = tutorialService.findByTutorialCriteria(tutorialCriteria)

        assertEquals(1, tutors.size)
        assertEquals(
            TutorialDTO(2, "Name Tutorial2", "Description Tutorial2", true, true),
            tutors.get(0))
    }

//    @Test
//    fun getByDslFilterIdsAndName() {
//        val productService = ProductServiceImpl(productRepository, groupProductService)
//        val filter = ProductFilter(listOf(31L, 32L), "Desktop2")
//
//        val filteredProducts = productService.getByFilter(filter)
//
//        assertEquals(1, filteredProducts.size)
//        assertEquals(ProductDTO(32, "Desktop2", 3), filteredProducts.get(0))
//    }
//
//    @Test
//    fun checkSortByName_ByDslFilterByName() {
//        val productService = ProductServiceImpl(productRepository, groupProductService)
//        val filter = ProductFilter(listOf(51), "Monitor1", listOf("name")) // for Desktop1, HDD1, Monitor1, Notebook1
//
//        val filteredProducts = productService.getByFilter(filter)
//
//        assertEquals(1, filteredProducts.size)
//        assertEquals(51, filteredProducts.get(0).n)
//        assertEquals("Monitor1", filteredProducts.get(0).name)
//    }
//
//    @Test
//    fun checkSortByN_ByDslFilterByName() {
//        val productService = ProductServiceImpl(productRepository, groupProductService)
//        val filter = ProductFilter(listOf(), "%Monitor%", listOf("n")) // for Desktop1, HDD1, Monitor1, Notebook1
//
//        val filteredProducts = productService.getByFilter(filter)
//
//        assertEquals(2, filteredProducts.size)
//        assertEquals(ProductDTO(51, "Monitor1", 5), filteredProducts.get(0))
//        assertEquals(ProductDTO(52, "Monitor2", 5), filteredProducts.get(1))
//    }
//
//    @Test
//    fun checkWithDefaultSortByDslFilterByName() {
//        val productService = ProductServiceImpl(productRepository, groupProductService)
//        val filter = ProductFilter(listOf(), "1") // for Desktop1, HDD1, Monitor1, Notebook1
//
//        val filteredProducts = productService.getByFilter(filter)
//
//        assertEquals(4, filteredProducts.size)
//        assertEquals("Desktop1", filteredProducts.get(0).name)
//        assertEquals("HDD1", filteredProducts.get(1).name)
//        assertEquals("Monitor1", filteredProducts.get(2).name)
//        assertEquals("Notebook1", filteredProducts.get(3).name)
//    }
//
//    @Test
//    fun getByName() {
//        val productService = ProductServiceImpl(productRepository, groupProductService)
//
//        val products = productService.getByName("Desktop")
//
//        assertEquals(3, products.size)
//        assertEquals("Desktop1", products.get(0).name)
//        assertEquals("Desktop2", products.get(1).name)
//        assertEquals("Desktop3", products.get(2).name)
//    }
//
//    @Test
//    fun existByN() {
//        val productService = ProductServiceImpl(productRepository, groupProductService)
//
//        assertTrue(productService.existByN(31L))
//    }
//
//    @Test
//    fun notExistByN() {
//        val productService = ProductServiceImpl(productRepository, groupProductService)
//
//        assertFalse(productService.existByN(0L))
//    }

}