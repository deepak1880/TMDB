package com.shaikhabdulgani.tmdb.search.domain.model

enum class SearchType(private val value: String) {
    MOVIE("movie"),
    SERIES("tv"),
    PERSON("person"),
    ALL("multi")
    ;

    fun getValue(): String {
        return value
    }

    companion object {
        private val map = mutableMapOf<String, SearchType>()

        init {
            SearchType.entries.forEach {
                map[it.value] = it
            }
        }

        fun parse(value: String?): SearchType {
            if (value == null) return ALL
            return map.getOrDefault(value, ALL)
        }
    }
}