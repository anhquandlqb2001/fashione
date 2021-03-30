package vn.quanprolazer.fashione.domain

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    @SerializedName("productId")
    val productId: String, //Document ID is actually the user id
    @SerializedName("categoryId")
    val categoryId: String,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("productImageUrl")
    val productImageUrl: String,
    @SerializedName("productPrice")
    val productPrice: String
) : Parcelable

data class ProductContainer(
    @SerializedName("hits")
    val products: List<Product>
)

fun fromJson(json: String): ProductContainer {
    return Gson().fromJson(json, ProductContainer::class.java)
}


@Parcelize
data class Category(
    @SerializedName("message")
    val categoryId: String,
    @SerializedName("message")
    val categoryName: String
) : Parcelable