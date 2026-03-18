package com.example.tictactoemobile.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private val gson = Gson()

fun boardToString(board: List<List<Int>>): String {
    return gson.toJson(board)
}

fun stringToBoard(json: String): List<List<Int>> {
    if(json.isBlank()) return emptyList()
    val type = object : TypeToken<List<List<Int>>>() {}.type
    return gson.fromJson(json, type)
}