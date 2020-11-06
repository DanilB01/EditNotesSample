package ru.tsu.sampleproject

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao{
    @Insert
    fun addCategory(category: Category)

    @Delete
    fun deleteCategory(category: Category)

    @Query("SELECT * FROM category_table")
    fun getAll(): List<Category>

}