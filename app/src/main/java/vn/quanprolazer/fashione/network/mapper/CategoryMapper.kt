/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.mapper

import vn.quanprolazer.fashione.domain.model.Category
import vn.quanprolazer.fashione.network.dto.NetworkCategory

object CategoryListMapper : ListMapper<NetworkCategory, Category> {
    override fun map(input: List<NetworkCategory>): List<Category> {
        return input.map {
            Category(
                it.id,
                it.name
            )
        }
    }
}