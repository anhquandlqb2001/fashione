/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.data.domain.model.OrderStatus
import vn.quanprolazer.fashione.data.domain.model.Purchase
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.data.domain.repository.PurchaseRepository
import javax.inject.Inject

@HiltViewModel
class PurchaseViewModel @Inject constructor(private val purchaseRepository: PurchaseRepository) :
        ViewModel() {

    private val _purchaseItems: MutableLiveData<Resource<List<Purchase>>> by lazy {
        val liveData = MutableLiveData<Resource<List<Purchase>>>()

        viewModelScope.launch {
            liveData.value = purchaseRepository.getPurchaseItems(OrderStatus.CONFIRMING)
        }
        return@lazy liveData
    }

    val purchaseItems: LiveData<Resource<List<Purchase>>> get() = _purchaseItems

}