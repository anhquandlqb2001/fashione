/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.repositories

import vn.quanprolazer.fashione.domain.models.DistrictOrTown
import vn.quanprolazer.fashione.domain.models.ProvinceOrCity
import vn.quanprolazer.fashione.domain.models.SubdistrictOrVillage

interface NetworkRepository {
    suspend fun getProvincesOrCities(): List<ProvinceOrCity>

    suspend fun getDistrictsOrTowns(parentCode: String): List<DistrictOrTown>

    suspend fun getSubdistrictsOrVillages(parentCode: String): List<SubdistrictOrVillage>
}