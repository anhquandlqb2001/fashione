/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.service

import com.google.firebase.firestore.Source
import vn.quanprolazer.fashione.domain.model.Result
import vn.quanprolazer.fashione.network.dto.NetworkCategory

interface CategoryService {

    suspend fun getCategoryList(source: Source = Source.DEFAULT): Result<List<NetworkCategory>>

}