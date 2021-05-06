/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import vn.quanprolazer.fashione.data.domain.model.DistrictOrTown
import vn.quanprolazer.fashione.data.domain.model.ProvinceOrCity
import vn.quanprolazer.fashione.data.domain.model.SubdistrictOrVillage
import vn.quanprolazer.fashione.data.domain.repository.NetworkRepository
import vn.quanprolazer.fashione.data.network.service.PickupAddressService
import java.lang.Exception
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