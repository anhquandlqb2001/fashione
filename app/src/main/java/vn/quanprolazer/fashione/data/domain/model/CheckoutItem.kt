/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CheckoutItem(
    val id: String,
    val productId: String,
    val userId: String,
    val variantId: String,
    val variantOptionId: String,
    val productName: String,
    val variantName: String,
    val variantValue: String,
    var quantity: Int,
    val price: String,
    var cartItemImg: String,
): Parcelable