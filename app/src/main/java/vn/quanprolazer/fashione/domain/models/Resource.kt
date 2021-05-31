/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.models


sealed class Resource<out R> {
    data class Success<out T>(val data: T) : Resource<T>()
    object Loading : Resource<Nothing>()
    data class Error(val exception: Exception) : Resource<Nothing>()
}
