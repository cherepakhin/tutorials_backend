package ru.perm.v.tutorials.mapper

import ru.perm.v.tutorials.dto.TutorialDTO
import ru.perm.v.tutorials.entity.TutorialEntity

object MapperTutorial {

    fun mapFromDtoToEntity(dto: TutorialDTO) = TutorialEntity(
        dto.n,
        dto.name,
        dto.description,
        dto.published,
        dto.submitted
    )

    fun mapFromEntityToDto(entity: TutorialEntity) = TutorialDTO(
        entity.n,
        entity.name,
        entity.description,
        entity.published,
        entity.submitted
    )
}