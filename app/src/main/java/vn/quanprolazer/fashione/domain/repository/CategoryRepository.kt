/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.repository

import vn.quanprolazer.fashione.domain.model.Category

interface CategoryRepository {

    suspend fun getCategoryList(): List<Category>

}