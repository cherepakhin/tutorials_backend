package ru.perm.v.tutorials.filter

/**
 * Filter for Tutorial with listN, name and listSortBy
 * @param listN - list of Tutorial N
 * @param name - name of Tutorial
 * @param listSortBy - list of sort fields, default is "name"
 */
data class TutorialCriteria(
    val listN: List<Long> = ArrayList(),
    var name: String = "",
    var listSortBy: List<String> = listOf("name")
)
