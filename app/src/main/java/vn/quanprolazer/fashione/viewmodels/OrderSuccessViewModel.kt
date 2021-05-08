/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderSuccessViewModel : ViewModel() {
    private val _navigateToHome: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val navigateToHome: LiveData<Boolean> get() = _navigateToHome

    fun doneNavigateToHome() {
        _navigateToHome.value = null
    }

    fun onNavigateToHome() {
        _navigateToHome.value = true
    }
}