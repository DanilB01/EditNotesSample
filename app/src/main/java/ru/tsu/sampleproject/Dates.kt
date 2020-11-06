package ru.tsu.sampleproject

data class Dates(
    val type: Int, // 0 - category, 1 - note
    val category: Category?,
    val note: Note?
)