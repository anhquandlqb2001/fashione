/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.service

import vn.quanprolazer.fashione.network.dto.NetworkCategory

interface CategoryService {

    suspend fun getCategoryList(): List<NetworkCategory>

}