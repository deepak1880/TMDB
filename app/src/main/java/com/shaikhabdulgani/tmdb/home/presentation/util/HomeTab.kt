package com.shaikhabdulgani.tmdb.home.presentation.util

enum class HomeTab(private val value: String) {
    MOVIES("Movies"),
    SERIES("Series"),
//    ANIMES("Animes"),
//    NOVELS("Novels")
    ;

    fun getValue(): String {
        return value
    }

    companion object {
        private val map = mutableMapOf<String, HomeTab>()

        init {
            HomeTab.entries.forEach {
                map[it.getValue()] = it
            }
        }

        fun getTab(value: String): HomeTab {
            return map.getOrDefault(value, MOVIES)
        }

        fun fromOrdinal(ordinal: Int): HomeTab{
            return HomeTab.entries[ordinal]
        }
    }
}