package me.intuit.cat.domain.model

import me.intuit.cat.domain.model.Breed
import java.io.Serializable

data class BreedDetails(
    var id: String,
    var url: String,
    var breeds: List<Breed>
) : Serializable {

}