package vn.quanprolazer.fashione.ui.home

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.Category
import vn.quanprolazer.fashione.domain.Product
import vn.quanprolazer.fashione.network.*


class HomeViewModel : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products


    private val _navigateToSearchResult = MutableLiveData<Category>()
    val navigateToSearchResult: LiveData<Category> = _navigateToSearchResult

    fun onClickCategory(category: Category) {
        _navigateToSearchResult.value = category
    }

    fun doneNavigate() {
        _navigateToSearchResult.value = null
        _navigateToSearchResultByText.value = null
    }


    private val _navigateToSearchResultByText = MutableLiveData<String>()
    val navigateToSearchResultByText: LiveData<String> = _navigateToSearchResultByText


    val searchText = MutableLiveData<String>()

    fun onSearch() {
        _navigateToSearchResultByText.value = searchText.value
    }


    init {
        viewModelScope.launch {
            _categories.value = FashioneCategoryService.getCategories()
            _products.value = FashioneProductService.getProducts()

            // add product - for test
//            FashioneProductAdminService.addProduct(
//                NetworkProduct(null, "HcAe5wPDtBEhd8Ukye2K",
//                    "Quần jean cardino",
//                "https://cardino.vn/wp-content/uploads/quan-jean-QJEA005-xanhtri.jpg",
//                    "250000"
//                )
//            )
        }
    }
}


class SearchViewModel(private val viewModel: HomeViewModel) : BaseObservable() {
    @Bindable
    var searchText: String? = null
        set(value) {
            viewModel.searchText.value = value
            field = value
        }
}