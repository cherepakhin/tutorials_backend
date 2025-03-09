package ru.perm.v.tutorials.consts

class ErrMessages {
    companion object {
        const val ID_NOT_FOUND = "id not found"
        const val ID_MUST_POSITIVE = "Field id must be greater than 0"
        const val NOT_FOUND_ANY_GROUP = "Groups is empty."
        const val NOT_FOUND_GROUP_BY_ID = "Group product not found with id=%s"
        const val NOT_FOUND_GROUP_BY_NAME = "Group product not found with name=%s"

        const val NOT_FOUND_PRODUCT_BY_ID = "Product not found with id=%s"
        const val FIELD_TUTORIAL_NAME_EMPTY = "Field 'name' in TutorialDTO is empty"
        const val HAVE_SUBGROUPS = "Group product with id: %s contains subgroups. Remove them first."
        const val HAVE_PRODUCTS = "Group product with id: %s contains products. Remove them to other group first."
    }
}