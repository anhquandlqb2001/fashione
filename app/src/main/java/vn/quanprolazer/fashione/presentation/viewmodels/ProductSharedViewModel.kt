/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductSharedViewModel : ViewModel() {
    /**
     * Variable to store exception
     */
    private val _exceptionMessage: MutableLiveData<String> by lazy {
        MutableLiveData()
    }
    val exceptionMessage: LiveData<String> get() = _exceptionMessage
    fun setExceptionMessage(message: String) {
        _exceptionMessage.value = message
    }

    /**
     * Variable to store success message
     */
    private val _successMessage: MutableLiveData<String> by lazy {
        MutableLiveData()
    }
    val successMessage: LiveData<String> get() = _successMessage
    fun setSuccessMessage(message: String) {
        _successMessage.value = message
    }

    fun doneObserve() {
        _successMessage.value = null
        _exceptionMessage.value = null
    }

}