package me.intuit.cat.data.local.entity

import com.google.gson.annotations.SerializedName
import me.intuit.cat.domain.model.BreedDetails
import java.io.Serializable

data class BreedDetailDto (
    @SerializedName("id")
    var id: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("breeds")
    var breeds: List<BreedDto>,
) : Serializable {

}

fun BreedDetailDto.toDomain(): BreedDetails {
    return BreedDetails(
        id = id,
        url = url,
        breeds= breeds.mapToDomain()
    )
}

/*fun List<BreedImageDto>.mapFromBreedListModel(): List<BreedImage> {
    return this.map {
        it.toDomain()
    }
}*/

fun List<BreedDetailDto>.mapToDomain(): List<BreedDetails> {
    return this.map {
        it.toDomain()
    }
}
