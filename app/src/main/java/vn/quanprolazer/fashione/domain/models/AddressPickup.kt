/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface BaseAddressPickup {
    val code: String
    val name: String
    val nameWithType: String
}

data class BaseAddressPickupImpl(
    override val code: String,
    override val name: String,
    override val nameWithType: String
) : BaseAddressPickup


// tinh / tp
@Serializable
data class ProvinceOrCity(
    override val code: String,
    override val name: String,
    val slug: String,
    val type: String,
    @SerialName("name_with_type") override val nameWithType: String
) : BaseAddressPickup

//// quan huyen
@Serializable
data class DistrictOrTown(
    override val code: String,
    override val name: String,
    @SerialName("name_with_type") override val nameWithType: String,
    @SerialName("parent_code") val parentCode: String,
    val path: String,
    @SerialName("path_with_type") val pathWithType: String,
    val slug: String,
    val type: String
) : BaseAddressPickup

// Phuong xa
@Serializable
data class SubdistrictOrVillage(
    override val code: String,
    override val name: String,
    @SerialName("name_with_type") override val nameWithType: String,
    @SerialName("path") val path: String,
    @SerialName("path_with_type") val pathWithType: String,
    @SerialName("parent_code") val parentCode: String,
    val slug: String,
    val type: String
) : BaseAddressPickup