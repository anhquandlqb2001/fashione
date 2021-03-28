package vn.quanprolazer.fashione.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class SearchResultViewModelFactory(
    private val categoryId: String, private val query: String) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchResultViewModel::class.java)) {
            return SearchResultViewModel(categoryId, query) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}