package ru.perm.v.tutorials.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ru.perm.v.tutorials.dto.TutorialDTO
import ru.perm.v.tutorials.repository.TutorialRepository

@DataJpaTest
class TutorialServiceDataJpaTest {
    @Autowired
    lateinit var tutorialRepository: TutorialRepository

    @Test
    fun forDEMO() {
        val tutorialService = TutorialServiceImpl(tutorialRepository)

        assertEquals(4, tutorialService.getAll().size)
    }

    //TODO: test for integration tests
    @Test
    fun getByDslFilterByName() {
        val tutorialService = TutorialServiceImpl(tutorialRepository)
        val NAME = "Tutorial"

        val tutorials = tutorialService.findByNameContainingOrderByName(NAME)

        assertEquals(4, tutorials.size)
    }

    @Test
    fun getByDslFilterByName1() {
        val tutorialService = TutorialServiceImpl(tutorialRepository)
        val NAME = "Tutorial1"

        val tutorials = tutorialService.findByNameContainingOrderByName(NAME)

        assertEquals(1, tutorials.size)
        assertEquals(
            TutorialDTO(1, "Name Tutorial1", "Description Tutorial1", true, true),
            tutorials.get(0)
        )
    }
//
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