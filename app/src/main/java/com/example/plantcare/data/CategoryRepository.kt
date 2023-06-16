package com.example.plantcare.data

import com.example.plantcare.model.categories.CategoriesData
import com.example.plantcare.model.categories.Category
import com.example.plantcare.model.histories.History
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CategoryRepository {
    private val categories = mutableListOf<Category>()
    private val histories = mutableListOf<History>()

    init {
        if (categories.isEmpty()){
            categories.addAll(CategoriesData.categories)
        }
    }

    fun getAllCategories() : Flow<List<Category>> {
        return flowOf(categories)
    }

    fun getCategoryById(id : Int) : Category {
        return categories.first {
            it.id == id
        }
    }

    fun getAllHistories() : Flow<List<History>> {
        return flowOf(histories)
    }

    fun getHistoryById(id : Int) : History {
        return histories.first {
            it.id == id
        }
    }

    companion object {
        @Volatile
        private var instance: CategoryRepository? = null

        fun getInstance(): CategoryRepository =
            instance ?: synchronized(this) {
                CategoryRepository().apply {
                    instance = this
                }
            }
    }

}