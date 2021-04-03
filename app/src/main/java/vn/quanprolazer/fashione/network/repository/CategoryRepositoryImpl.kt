/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import vn.quanprolazer.fashione.domain.model.Category
import vn.quanprolazer.fashione.domain.model.Result
import vn.quanprolazer.fashione.domain.model.SimpleResult
import vn.quanprolazer.fashione.domain.repository.CategoryRepository
import vn.quanprolazer.fashione.network.dto.NetworkCategory
import vn.quanprolazer.fashione.network.mapper.CategoryListMapper
import vn.quanprolazer.fashione.network.mapper.Mapper
import vn.quanprolazer.fashione.network.service.CategoryService


class CategoryRepositoryImpl(
    private val categoryService: CategoryService,
    private val categoryListMapper: (List<NetworkCategory>) -> List<Category>
) : CategoryRepository {
    override suspend fun getCategoryList(): SimpleResult<List<Category>> {
        return categoryService.getCategoryList().fold(
            success = { dto -> Result.Success(categoryListMapper(dto))},
            failure = { throwable -> Result.Failure(throwable) }
        )
    }
}