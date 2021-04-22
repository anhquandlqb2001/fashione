/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Loading<out T>(val data: T?) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}
