package ru.perm.v.tutorials.service.impl

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import ru.perm.v.tutorials.dto.TutorialDTO
import ru.perm.v.tutorials.entity.TutorialEntity
import ru.perm.v.tutorials.repository.TutorialRepository
import kotlin.test.assertEquals
import kotlin.test.assertFalse

internal class TutorialServiceImplMockTest {

    @Mock
    private lateinit var tutorialRepository: TutorialRepository


    @InjectMocks
    // @InjectMocks САМ создает экземпляр service
    // c "инъектированным" MOCK productRepository, GroupProductService, EntityManager
    // EntityManager для inject находит в контексте
    private lateinit var tutorialService: TutorialServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun existByN() {
        val TUTORIAL_N = 1L
        Mockito.`when`(tutorialRepository.existsById(TUTORIAL_N))
            .thenReturn(true)

        assertTrue(tutorialService.existById(TUTORIAL_N))
    }

    @Test
    fun getByNotExistN() {
        val TUTORIAL_N = 1L
        Mockito.`when`(tutorialRepository.existsById(TUTORIAL_N))
            .thenReturn(false)

        assertFalse(tutorialService.existById(TUTORIAL_N))
    }

    @Test
    fun getByN() {
        val TUTORIAL_N = 1L
        val tutorialEntity = TutorialEntity()
        tutorialEntity.n = TUTORIAL_N
        Mockito.`when`(tutorialRepository.existsById(TUTORIAL_N))
            .thenReturn(true)
        Mockito.`when`(tutorialRepository.getById(TUTORIAL_N))
            .thenReturn(tutorialEntity)

        val tutorialDto = tutorialService.getByN(TUTORIAL_N)

        assertEquals(TutorialDTO(TUTORIAL_N, "", ""), tutorialDto)
        Mockito.verify(tutorialRepository, times(1)).getById(TUTORIAL_N)
        Mockito.verify(tutorialRepository, times(1)).existsById(TUTORIAL_N)
    }


    @Test
    fun create() {
        val TUTORIAL_NEXT_N = 200L
        val NAME = "NAME"
        val DESCRIPTION = "DESCRIPTION"
        val PUBLISHED = true
        val SUBSCRIBED = true

        Mockito.`when`(tutorialRepository.getNextN()).thenReturn(TUTORIAL_NEXT_N)
        val saved = TutorialEntity(TUTORIAL_NEXT_N, NAME, DESCRIPTION, PUBLISHED, SUBSCRIBED)
        Mockito.`when`(tutorialRepository.save(saved)).thenReturn(saved)

        val createdDTO = tutorialService.create(TutorialDTO(TUTORIAL_NEXT_N, NAME, DESCRIPTION, PUBLISHED, SUBSCRIBED))

        assertEquals(TutorialDTO(TUTORIAL_NEXT_N, NAME, DESCRIPTION, PUBLISHED, SUBSCRIBED), createdDTO)
        Mockito.verify(tutorialRepository, times(1)).save(saved)
    }

    @Test
    fun getEmptyAll() {
        Mockito.`when`(tutorialRepository.findAllByOrderByNAsc())
            .thenReturn(listOf())

        val tutorials = tutorialService.getAll()

        assertEquals(0, tutorials.size)
        Mockito.verify(tutorialRepository, times(1)).findAll()
    }

    @Test
    fun getAll() {
        val entites = listOf(
            TutorialEntity(1L, "NAME", "DESCRIPTION", true, true),
            TutorialEntity(2L, "NAME", "DESCRIPTION", true, true)
        )
        Mockito.`when`(tutorialRepository.findAll())
            .thenReturn(entites)

        val tutorials = tutorialService.getAll()

        assertEquals(2, tutorials.size)
        Mockito.verify(tutorialRepository, times(1)).findAll()

        assertEquals(
            listOf<TutorialDTO>(
                TutorialDTO(1L, "NAME", "DESCRIPTION", true, true),
                TutorialDTO(2L, "NAME", "DESCRIPTION", true, true)
            ),
            tutorials
        )
    }

    @Test
    fun getAllSortedByN() {
        val entites = listOf(
            TutorialEntity(1L, "NAME", "DESCRIPTION", true, true),
            TutorialEntity(2L, "NAME", "DESCRIPTION", true, true)
        )
        Mockito.`when`(tutorialRepository.findAllByOrderByNAsc())
            .thenReturn(entites)

        val tutorials = tutorialService.getAllSortedByN()

        assertEquals(2, tutorials.size)

        assertEquals(
            listOf<TutorialDTO>(
                TutorialDTO(1L, "NAME", "DESCRIPTION", true, true),
                TutorialDTO(2L, "NAME", "DESCRIPTION", true, true)
            ),
            tutorials
        )
        Mockito.verify(tutorialRepository, times(1)).findAllByOrderByNAsc()
    }

    @Test
    fun update() {
        val TUTORIAL_N = 100L
        val NAME = "NAME"
        val DESCRIPTION = "DESCRIPTION"
        val PUBLISHED = true
        val SUBSCRIBED = true

        Mockito.`when`(tutorialRepository.existsById(TUTORIAL_N)).thenReturn(true)
        val saved = TutorialEntity(TUTORIAL_N, NAME, DESCRIPTION, PUBLISHED, SUBSCRIBED)
        Mockito.`when`(tutorialRepository.save(saved)).thenReturn(saved)
        val updatedTutorial = tutorialService.update(TutorialDTO(TUTORIAL_N, NAME, DESCRIPTION, PUBLISHED, SUBSCRIBED))

        assertEquals(TutorialDTO(TUTORIAL_N, NAME, DESCRIPTION, PUBLISHED, SUBSCRIBED), updatedTutorial)
        Mockito.verify(tutorialRepository, times(1)).save(saved)
    }

    @Test
    fun updateNotExist() {
        val TUTORIAL_N = 100L
        val NAME = "NAME"
        val DESCRIPTION = "DESCRIPTION"
        val PUBLISHED = true
        val SUBSCRIBED = true

        Mockito.`when`(tutorialRepository.existsById(TUTORIAL_N)).thenReturn(false)
        val excpt = assertThrows<Exception> {
            tutorialService.update(TutorialDTO(TUTORIAL_N, NAME, DESCRIPTION, PUBLISHED, SUBSCRIBED))
        }
        assertEquals("Tutorial with n=100 not exist", excpt.message)
        Mockito.verify(tutorialRepository, never()).save(any())
    }

    @Test
    fun deleteForNotExist() {
        val TUTORIAL_N = 100L

        Mockito.`when`(tutorialRepository.existsById(TUTORIAL_N))
            .thenReturn(false)

        val excpt = assertThrows<Exception> {
            tutorialService.delete(TUTORIAL_N)
        }

        assertEquals("Tutorial with n=100 not exist", excpt.message)
    }

    @Test
    fun deleteForExistTutorial() {
        val TUTORIAL_N = 100L

        Mockito.`when`(tutorialRepository.existsById(TUTORIAL_N))
            .thenReturn(true)

        tutorialService.delete(TUTORIAL_N)

        Mockito.verify(tutorialRepository, times(1)).deleteById(TUTORIAL_N)
    }
}