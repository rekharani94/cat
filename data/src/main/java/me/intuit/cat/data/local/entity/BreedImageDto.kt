package me.intuit.cat.data.local.entity

import com.google.gson.annotations.SerializedName
import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.model.BreedImage
import java.io.Serializable

data class BreedImageDto(
    @SerializedName("id")
    var id: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("breeds")
    var breeds: List<BreedDto>

) : Serializable {

}

fun BreedImageDto.toDomain(): BreedImage {
    return BreedImage(
        id = id,
        url = url,
        breeds = breeds.mapToDomain()
    )
}


fun List<BreedImageDto>.mapToDomain(): List<BreedImage> {
    return this.map {
        it.toDomain()
    }
}
