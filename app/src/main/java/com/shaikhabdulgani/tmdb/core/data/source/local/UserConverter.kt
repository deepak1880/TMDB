package com.shaikhabdulgani.tmdb.core.data.source.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserConverter {

    private val gson = Gson()

    @TypeConverter
    fun toList(string: String): List<String> {
        return gson.fromJson(string, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return gson.toJson(list.toTypedArray())
    }
}