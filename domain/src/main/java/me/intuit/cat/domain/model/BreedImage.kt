package me.intuit.cat.domain.model

import java.io.Serializable

data class BreedImage(
    var id: String,
    var url: String,
    var breeds: List<Breed>,
) : Serializable {

}