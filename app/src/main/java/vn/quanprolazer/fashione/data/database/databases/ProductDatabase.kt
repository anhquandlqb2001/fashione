/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.database.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import vn.quanprolazer.fashione.data.database.dao.ProductMostViewIdDao
import vn.quanprolazer.fashione.data.database.models.Converters
import vn.quanprolazer.fashione.data.database.models.ProductMostViewIdEntity

@Database(entities = [ProductMostViewIdEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productMostViewIdDao(): ProductMostViewIdDao
}