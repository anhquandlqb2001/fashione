/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.repository

import vn.quanprolazer.fashione.data.domain.model.DistrictOrTown
import vn.quanprolazer.fashione.data.domain.model.ProvinceOrCity
import vn.quanprolazer.fashione.data.domain.model.SubdistrictOrVillage

interface NetworkRepository {
    suspend fun getProvincesOrCities() : List<ProvinceOrCity>

    suspend fun getDistrictsOrTowns(parentCode: String) : List<DistrictOrTown>

    suspend fun getSubdistrictsOrVillages(parentCode: String) : List<SubdistrictOrVillage>
}