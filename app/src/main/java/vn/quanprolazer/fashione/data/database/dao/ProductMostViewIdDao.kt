/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vn.quanprolazer.fashione.data.database.models.ProductMostViewIdEntity

@Dao
interface ProductMostViewIdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(product: ProductMostViewIdEntity)

    @Query("SELECT * FROM ProductMostViewIdEntity")
    fun loadProductMostView(): ProductMostViewIdEntity

    @Query("SELECT COUNT(*) FROM ProductMostViewIdEntity WHERE lastUpdate >= :timeout")
    fun hasId(timeout: Long): Int
}