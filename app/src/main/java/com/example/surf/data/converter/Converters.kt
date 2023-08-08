package com.example.surf.data.converter

import androidx.room.TypeConverter


class Converters {

    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun toString(list: List<String>): String {
        return list.joinToString(",")
    }
}