/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.dto

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import timber.log.Timber
import vn.quanprolazer.fashione.domain.model.Category

@Serializable
data class NetworkCategory(
    @get:PropertyName("id")
    @SerialName("id")
    var id: String?,
    @get:PropertyName("name")
    @SerialName("name")
    var name: String?
) {
    companion object {
        /**
         * Convert network result to domain object
         */
        fun DocumentSnapshot.asDomainCategory(): Category? {
            return try {
                val name = requireNotNull(getString("name"))
                Category(id, name)
            } catch (e: Exception) {
                Timber.e(e)
                null
            }
        }
    }
}