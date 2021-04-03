/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.mapper

import vn.quanprolazer.fashione.domain.model.Category
import vn.quanprolazer.fashione.network.dto.NetworkCategory

class CategoryListMapper(
    private val categoriesList: NullableInputListMapper<NetworkCategory, Category>
) : Mapper<NetworkCategory, Category> {
    override fun map(input: NetworkCategory): Category {
        return Category(
            input.id.orEmpty(),
            input.name.orEmpty()
        )
    }
}