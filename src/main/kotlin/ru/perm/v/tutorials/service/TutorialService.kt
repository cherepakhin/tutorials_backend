package ru.perm.v.tutorials.service

import ru.perm.v.tutorials.dto.TutorialDTO

interface TutorialService {
    fun create(dto: TutorialDTO): TutorialDTO
    fun getByN(n: Long): TutorialDTO
    fun getAll(): List<TutorialDTO>
    fun getAllSortedByN(): List<TutorialDTO>
    fun update(dto: TutorialDTO): TutorialDTO
    fun delete(n: kotlin.Long)
    fun existById(n: Long): Boolean
}