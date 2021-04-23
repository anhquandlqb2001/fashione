/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.service

import com.google.firebase.firestore.Source
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.data.network.dto.NetworkCategory

interface CategoryService {

    suspend fun getCategoryList(source: Source = Source.DEFAULT): Resource<List<NetworkCategory>>

}