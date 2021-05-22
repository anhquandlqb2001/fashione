/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _navigateToNotification: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val navigateToNotification: LiveData<Boolean> get() = _navigateToNotification

    fun onClickNavigateToNotification() {
        _navigateToNotification.value = true
    }

    fun doneNavigateToNotification() {
        _navigateToNotification.value = null
    }

}