package ru.tsu.sampleproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note (
    val theme: String,
    val title: String,
    val description: String,
    val other: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)