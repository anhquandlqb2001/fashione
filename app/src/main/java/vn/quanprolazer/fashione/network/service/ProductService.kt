/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.service

import com.google.firebase.firestore.Source
import vn.quanprolazer.fashione.network.dto.NetworkProduct
import vn.quanprolazer.fashione.network.dto.NetworkProductDetail

interface ProductService {

    suspend fun getProducts(source: Source = Source.DEFAULT): List<NetworkProduct>

    suspend fun getProductsByCategoryId(categoryId: String): List<NetworkProduct>

    suspend fun getProductDetailByProductId(productId: String): NetworkProductDetail

}