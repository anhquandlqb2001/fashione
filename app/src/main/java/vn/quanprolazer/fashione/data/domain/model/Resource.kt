/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model


sealed class Resource<out R> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Loading<out T>(val data: T?) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
}

fun <T>fromResult(data : Resource<T>) : Resource<T> {
    return when (data) {
        is Resource.Success -> Resource.Success(data.data)
        is Resource.Loading -> Resource.Loading(null)
        is Resource.Error -> Resource.Error(data.exception)
    }
}