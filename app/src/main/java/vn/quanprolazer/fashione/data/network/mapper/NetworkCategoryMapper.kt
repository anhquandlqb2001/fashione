/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.mapper

import vn.quanprolazer.fashione.data.domain.model.Category
import vn.quanprolazer.fashione.data.mapper.ListMapper
import vn.quanprolazer.fashione.data.network.dto.NetworkCategory

object NetworkCategoryListMapper : ListMapper<NetworkCategory, Category> {
    override fun map(input: List<NetworkCategory>): List<Category> {
        return input.map {
            Category(
                it.id,
                it.name
            )
        }
    }
}