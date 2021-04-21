/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.mapper

import vn.quanprolazer.fashione.data.domain.model.AddToCartItem
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.data.mapper.Mapper
import vn.quanprolazer.fashione.data.network.dto.NetworkCartItem

//object AddToCartItemMapper : Mapper<AddToCartItem, NetworkCartItem> {
//    override fun map(input: AddToCartItem): NetworkCartItem {
//        return NetworkCartItem(
//            input.productId,
//            input.userId,
//            input.variantId,
//            input.variantOptionId,
//            input.variantName,
//            input.variantValue,
//            input.quantity,
//            input.price
//        )
//    }
//}