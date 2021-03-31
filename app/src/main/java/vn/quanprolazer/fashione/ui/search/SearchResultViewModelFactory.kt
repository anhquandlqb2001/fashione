/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vn.quanprolazer.fashione.domain.Category


class SearchResultViewModelFactory(
    private val category: Category?, private val query: String?) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchResultViewModel::class.java)) {
            return SearchResultViewModel(category, query) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}