/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import vn.quanprolazer.fashione.domain.models.Order
import vn.quanprolazer.fashione.domain.models.OrderItem
import vn.quanprolazer.fashione.domain.models.OrderItemStatusType
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.OrderRepository
import vn.quanprolazer.fashione.domain.repositories.UserRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Inject
    private lateinit var userRepository: UserRepository

    @Inject
    private lateinit var orderRepository: OrderRepository

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    suspend fun onOrderClick() {

        val order = Order(
            userId = userRepository.getUser().value!!.uid,
            addressId = "123",
            shippingPriceTotal = "2000",
            productPriceTotal = "30000",
            createdAt = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")),
            status = OrderItemStatusType.CONFIRMING
        )

        val oritest = OrderItem(
            "123", "!23", 3
        )

        val orderItems = listOf(oritest)


        runBlocking {
            assertTrue((orderRepository.createOrder(order, orderItems) as Resource.Success).data)
        }

    }
}