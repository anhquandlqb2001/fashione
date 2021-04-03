/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}