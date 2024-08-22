package com.shaikhabdulgani.tmdb.search.domain.model

enum class MediaType(private val value: String) {
    MOVIE("movie"),
    SERIES("tv"),
    PERSON("person"),
    UNKNOWN("unknown")
    ;

    fun getValue(): String {
        return value
    }

    companion object {
        private val map = mutableMapOf<String, MediaType>()

        init {
            MediaType.entries.forEach {
                map[it.value] = it
            }
        }

        fun parse(value: String?): MediaType {
            if (value == null) return UNKNOWN
            return map.getOrDefault(value, UNKNOWN)
        }
    }
}