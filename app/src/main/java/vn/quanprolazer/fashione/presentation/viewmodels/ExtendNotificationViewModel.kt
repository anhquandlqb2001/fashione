/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class ExtendNotificationViewModel @AssistedInject constructor(@Assisted private val notificationTypeId: String) :
    ViewModel() {


    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(notificationTypeId: String): ExtendNotificationViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory, notificationTypeId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(notificationTypeId) as T
            }
        }
    }
}