/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.helpers

import java.util.concurrent.atomic.AtomicInteger

object NotificationID {
    private val c: AtomicInteger = AtomicInteger(0)
    val iD: Int
        get() = c.incrementAndGet()
}