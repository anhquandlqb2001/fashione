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

package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.Source
import vn.quanprolazer.fashione.data.network.models.NetworkCategory
import vn.quanprolazer.fashione.domain.models.Resource

interface CategoryService {

    suspend fun getCategoryList(source: Source = Source.DEFAULT): Resource<List<NetworkCategory>>

}