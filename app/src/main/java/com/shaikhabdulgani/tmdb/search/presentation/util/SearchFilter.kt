package com.shaikhabdulgani.tmdb.search.presentation.util

enum class SearchFilter(private val value: String) {
    ALL("All"),
    MOVIE("Movie"),
    SERIES("Series"),
    PERSON("Person")
    ;

    fun getValue(): String {
        return value
    }

    companion object {
        private val map = mutableMapOf<String, SearchFilter>()

        init {
            SearchFilter.entries.forEach {
                map[it.value] = it
            }
        }

        fun parse(value: String?): SearchFilter {
            if (value == null) return ALL
            return map.getOrDefault(value, ALL)
        }
    }
}