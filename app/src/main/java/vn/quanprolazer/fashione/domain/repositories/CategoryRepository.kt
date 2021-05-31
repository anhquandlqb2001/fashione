/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */


package vn.quanprolazer.fashione.domain.repositories

import vn.quanprolazer.fashione.domain.models.Category
import vn.quanprolazer.fashione.domain.models.Resource

interface CategoryRepository {

    suspend fun getCategoryList(): Resource<List<Category>>

}