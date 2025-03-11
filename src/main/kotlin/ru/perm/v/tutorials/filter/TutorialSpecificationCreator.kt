package ru.perm.v.tutorials.filter

import org.springframework.data.jpa.domain.Specification
import ru.perm.v.tutorials.entity.TutorialEntity
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

//@Component
object TutorialSpecificationCreator {
    fun getByCritery(tutorialCriteria: TutorialCriteria): Specification<TutorialEntity?> {
        return Specification { root: Root<TutorialEntity?>, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder ->
            val predicates: MutableList<Predicate> = ArrayList()
            if (!tutorialCriteria.name.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                    "%" + tutorialCriteria.name + "%"))
            }
            query.orderBy(criteriaBuilder.desc(root.get<Any>("name")))
            criteriaBuilder.and(*predicates.toTypedArray<Predicate>())
        }
    }
}