/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */


package vn.quanprolazer.fashione.data.domain.repository

import vn.quanprolazer.fashione.data.domain.model.Category
import vn.quanprolazer.fashione.data.domain.model.Resource

interface CategoryRepository {

    suspend fun getCategoryList(): Resource<List<Category>>

    suspend fun getCategoryListFromLocal(): Resource<List<Category>>

}