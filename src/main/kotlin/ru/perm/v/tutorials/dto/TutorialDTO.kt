package ru.perm.v.tutorials.dto

import ru.perm.v.tutorials.consts.ErrMessages
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

/**
 * DTO for Tutorial
*/
data class TutorialDTO(
    val n: Long = -1,
    @field:NotEmpty(message = ErrMessages.FIELD_TUTORIAL_NAME_EMPTY)
    val name: String = "-",
    val description: String = "",
    var published: Boolean = false,
    var submitted: Boolean = false
)
