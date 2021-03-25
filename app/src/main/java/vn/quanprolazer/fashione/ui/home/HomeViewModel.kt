package vn.quanprolazer.fashione.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.Category
import vn.quanprolazer.fashione.network.FashioneCategoryService

class HomeViewModel : ViewModel() {
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    init {
        viewModelScope.launch {
            _categories.value = FashioneCategoryService.getCategories()
        }
    }
}