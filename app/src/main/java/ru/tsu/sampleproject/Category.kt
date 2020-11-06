package ru.tsu.sampleproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category (
    val theme: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)