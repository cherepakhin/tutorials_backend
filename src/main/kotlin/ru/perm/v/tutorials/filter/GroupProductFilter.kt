package ru.perm.v.tutorials.filter

/**
 * Filter for GroupProduct with listN, name and listSortBy
 * @param listN - list of GroupProduct N
 * @param name - name of GroupProduct
 * @param listSortBy - list of sort fields, default is "name"
 */
data class GroupProductFilter(
    val listN: List<Long>, // list of GroupProduct N
    val name: String = "", // name of GroupProduct
    var listSortBy: List<String> = listOf("name") // list of sort fields
)
