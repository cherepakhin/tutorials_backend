package ru.perm.v.tutorials.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.perm.v.tutorials.entity.TutorialEntity

@Repository
@Transactional
interface TutorialRepository : JpaRepository<TutorialEntity, Long>,
    JpaSpecificationExecutor<TutorialEntity>, QuerydslPredicateExecutor<TutorialEntity> {

    fun findAllByOrderByNAsc(): List<TutorialEntity>

    @Query(value = "select max(t.n)+1 from tutorial t", nativeQuery = true)
    fun getNextN(): Long

    fun findByNameContainingOrderByName(name: String): List<TutorialEntity>
    fun findByNameContainingAndDescriptionContainingOrderByName(name: String, description: String): List<TutorialEntity>

    @Query(
        "select count(*) from TutorialEntity"
    )
    fun getCountOfTutorialNames(): Long

}