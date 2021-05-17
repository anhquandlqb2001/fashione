/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.data.network.services.retrofits.PickupAddressService
import vn.quanprolazer.fashione.domain.models.DistrictOrTown
import vn.quanprolazer.fashione.domain.models.ProvinceOrCity
import vn.quanprolazer.fashione.domain.models.SubdistrictOrVillage
import vn.quanprolazer.fashione.domain.repositories.NetworkRepository
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(private val addressService: PickupAddressService) :
    NetworkRepository {
    override suspend fun getProvincesOrCities(): List<ProvinceOrCity> {
        return withContext(Dispatchers.IO) {
            addressService.getProvincesOrCities()
        }
    }

    override suspend fun getDistrictsOrTowns(parentCode: String): List<DistrictOrTown> {
        return withContext(Dispatchers.IO) {
            addressService.getDistrictsOrTowns(parentCode)
        }
    }

    override suspend fun getSubdistrictsOrVillages(parentCode: String): List<SubdistrictOrVillage> {
        return withContext(Dispatchers.IO) {
            addressService.getSubDistrictsOrVillages(parentCode)
        }
    }
}