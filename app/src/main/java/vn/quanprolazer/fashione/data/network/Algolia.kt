/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network

import com.algolia.search.saas.Client
import com.algolia.search.saas.Index

object Algolia {
    var client: Client = Client("3NVM49I7G2", "d6588fc89921f8e880e645635f82ba32")
    var productIndex: Index = client.getIndex("dev_products")
}

