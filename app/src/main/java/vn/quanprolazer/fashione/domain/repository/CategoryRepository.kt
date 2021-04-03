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
import vn.quanprolazer.fashione.domain.model.Result
import vn.quanprolazer.fashione.domain.model.SimpleResult

interface CategoryRepository {

    suspend fun getCategoryList(): SimpleResult<List<Category>>

}