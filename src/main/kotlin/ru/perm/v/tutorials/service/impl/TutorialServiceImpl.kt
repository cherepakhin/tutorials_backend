package ru.perm.v.tutorials.service.impl

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.perm.v.tutorials.dto.TutorialDTO
import ru.perm.v.tutorials.entity.TutorialEntity
import ru.perm.v.tutorials.filter.TutorialCriteria
import ru.perm.v.tutorials.filter.TutorialSpecificationCreator
import ru.perm.v.tutorials.mapper.MapperTutorial
import ru.perm.v.tutorials.repository.TutorialRepository
import ru.perm.v.tutorials.service.TutorialService
import java.lang.String.format

//TODO: verify input params
@Service
class TutorialServiceImpl(val tutorialRepository: TutorialRepository) : TutorialService {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    override fun create(dto: TutorialDTO): TutorialDTO {
        val entity = MapperTutorial.mapFromDtoToEntity(dto)
        val n = tutorialRepository.getNextN()
        entity.n = n
        val createdEntity = tutorialRepository.save(MapperTutorial.mapFromDtoToEntity(dto))
        return MapperTutorial.mapFromEntityToDto(createdEntity)
    }

    override fun getByN(n: Long): TutorialDTO {
        if (!existById(n)) {
            throw Exception(format("Tutorial with :id not exist", n))
        }
        val tutorialEntity = tutorialRepository.getById(n)
        return MapperTutorial.mapFromEntityToDto(tutorialEntity)
    }

    override fun getAll(): List<TutorialDTO> {
        val entites = tutorialRepository.findAll()
        val dtos = entites.map { MapperTutorial.mapFromEntityToDto(it) }
        return dtos
    }

    override fun getAllSortedByN(): List<TutorialDTO> {
        val entites = tutorialRepository.findAllByOrderByNAsc()
        val dtos = entites.map { MapperTutorial.mapFromEntityToDto(it) }
        return dtos
    }

    override fun update(dto: TutorialDTO): TutorialDTO {
        if (!existById(dto.n)) {
            throw Exception(format("Tutorial with n=%s not exist", dto.n))
        }
        val savedEntity = tutorialRepository.save(MapperTutorial.mapFromDtoToEntity(dto))
        return MapperTutorial.mapFromEntityToDto(savedEntity)
    }

    override fun delete(n: Long) {
        if (!existById(n)) {
            throw Exception(format("Tutorial with n=%s not exist", n))
        }
        tutorialRepository.deleteById(n)
    }

    override fun existById(id: Long): Boolean {
        return tutorialRepository.existsById(id)
    }

    override fun findByNameContainingOrderByName(name: String): List<TutorialDTO> {
        val entites = tutorialRepository.findByNameContainingOrderByName(name)
        val dtos = entites.map { MapperTutorial.mapFromEntityToDto(it) }
        return dtos
    }

    override fun getCountOfTutorialNames(): Long {
        return tutorialRepository.getCountOfTutorialNames()
    }

    override fun getNextN(): Long {
        return tutorialRepository.getNextN()
    }

    override fun findByNameContainingAndDescriptionContainingOrderByName(
        name: String,
        description: String,
    ): List<TutorialDTO> {
        val entities = tutorialRepository.findByNameContainingAndDescriptionContainingOrderByName(name, description)

        val dtos = toDTOs(entities)
        return dtos
    }

    fun toDTOs(entities: List<TutorialEntity>): List<TutorialDTO> {
        return entities.map { MapperTutorial.mapFromEntityToDto(it) }
    }

    override fun findByTutorialCriteria(tutorialFilter: TutorialCriteria): List<TutorialDTO> {
        val tutorialSpecification = TutorialSpecificationCreator.getByCritery(tutorialFilter)
        val entities = tutorialRepository.findAll(tutorialSpecification)
        for (e in entities) {
            logger.info(e.toString())
        }
        return toDTOs(entities)
    }


}