package com.shaikhabdulgani.tmdb.search.domain.model

enum class ContentType(private val value: String) {
    MOVIE("movie"),
    SERIES("tv"),
    PERSON("person"),
    UNKNOWN("unknown")
    ;

    fun getValue(): String {
        return value
    }

    companion object {
        private val map = mutableMapOf<String, ContentType>()

        init {
            ContentType.entries.forEach {
                map[it.value] = it
            }
        }

        fun parse(value: String?): ContentType {
            if (value == null) return UNKNOWN
            return map.getOrDefault(value, UNKNOWN)
        }
    }
}