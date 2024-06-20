package me.intuit.cat.presentation.breed

import me.intuit.cat.domain.model.BreedImage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Fake {

    fun buildProducts(count: Int): Pair<String, List<BreedImage>> {
        val breedsimages = arrayListOf<BreedImage>()

        repeat(count) {
            val product = BreedImage(
                id = "",
               url=""
            )
            breedsimages.add(product)
        }

        val productsResponse = breedsimages
        return Pair(Json.encodeToString(productsResponse), productsResponse)
    }
}