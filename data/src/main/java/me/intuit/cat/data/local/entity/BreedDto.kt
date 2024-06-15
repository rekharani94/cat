package me.intuit.cat.data.local.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import me.intuit.cat.domain.model.Breed
import java.io.Serializable


data class BreedDto(val id: String, val name: String?, val description: String?,
                 val origin: String?, val temperament: String?, val hypoallergenic: Int, @SerializedName("wikipedia_url")
                    @Expose
                    var wikipedia_url: String? = null) :
    Serializable {

    override fun toString(): String = name?: ""
}

fun BreedDto.toDomain(): Breed {
    return Breed(
        id = id,
        description = description,
        origin = origin,
        temperament = temperament,
        name = name,
        hypoallergenic = hypoallergenic,
        wikipedia_url = wikipedia_url
    )
}



fun Breed.toBreedDto(): BreedDto {
    return BreedDto(
        id = id,
        description = description,
        origin = origin,
        temperament = temperament,
        name = name,
        hypoallergenic = hypoallergenic,
        wikipedia_url = wikipedia_url
    )
}
/*fun Breed.toEntity(): me.intuit.catsapp.data.local.entities.Breed {
    return Breed(
        id = id,
        description = description,
        origin = origin,
        temperament = temperament,
        name = name,
        hypoallergenic = hypoallergenic,
    )
}*/

/*fun Breed.toDomain():Breed {
    return Breed(
        id = id,
        description = description,
        origin = origin,
        temperament = temperament,
        name = name,
        hypoallergenic = hypoallergenic,
    )
}*/
fun List<Breed>.mapFromBreedListModel(): List<BreedDto> {
    return this.map {
        it.toBreedDto()
    }
}


fun List<BreedDto>.mapToDomain(): List<Breed> {
    return this.map {
        it.toDomain()
    }
}


