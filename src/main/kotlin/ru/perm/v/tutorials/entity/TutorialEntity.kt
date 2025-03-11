package ru.perm.v.tutorials.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tutorial")
open class TutorialEntity { // open - can be inherited and NEEDED FOR HIBERNATE (!!!)
    @Id
    @Column(name = "n", nullable = false)
    var n: Long = -1

    @Column(name = "name", nullable = false)
    var name: String = ""

    @Column(name = "description", nullable = false)
    var description: String = ""

    @Column(name = "published", nullable = false)
    var published: Boolean = false

    @Column(name = "submitted", nullable = false)
    var submitted: Boolean = false

    // Empty constructor needed for Hibernate
    constructor()

    constructor(
        n: Long,
        name: String,
        description: String,
        published: Boolean,
        submitted: Boolean
    ) {
        this.n = n
        this.name = name
        this.description = description
        this.published = published
        this.submitted = submitted
    }

    constructor(
        n: Long,
        name: String,
        description: String
    ) {
        this.n = n
        this.name = name
        this.description = description
    }

    override fun hashCode(): Int {
        return n.hashCode() + name.hashCode() + description.hashCode() +
                published.hashCode() + submitted.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TutorialEntity) return false
        if (n != other.n) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (published != other.published) return false
        if (submitted != other.submitted) return false
        return true
    }

    override fun toString(): String {
        return "TutorialEntity(n=$n, name='$name', description='$description', published=$published, submitted=$submitted)"
    }
}
