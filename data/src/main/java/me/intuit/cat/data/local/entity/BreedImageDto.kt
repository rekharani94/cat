package me.intuit.cat.data.local.entity

import com.google.gson.annotations.SerializedName
import me.intuit.cat.domain.model.BreedImage
import java.io.Serializable

data class BreedImageDto(
    @SerializedName("id")
    var id: String,
    @SerializedName("url")
    var url: String,

) : Serializable {

}

fun BreedImageDto.toDomain(): BreedImage {
    return BreedImage(
        id = id,
        url = url
    )
}


fun List<BreedImageDto>.mapToDomain(): List<BreedImage> {
    return this.map {
        it.toDomain()
    }
}
