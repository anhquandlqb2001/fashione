/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.ViewModel

class AddPickupAddressSharedViewModel : ViewModel() {

    private var _destination: Int = 0

    val destination: Int get() = _destination

    fun setDestinationToNavigate(des: Int) {
        _destination = des
    }

}