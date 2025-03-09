package ru.perm.v.tutorials.filter

/**
 * Filter for Product with listN, name and listSortBy
 * @param listN - list of Product N
 * @param name - name of Product
 * @param listSortBy - list of sort fields, default is "name"
 */
data class ProductFilter(
    val listN: List<Long>,
    val name: String = "",
    var listSortBy: List<String> = listOf("name")
)
