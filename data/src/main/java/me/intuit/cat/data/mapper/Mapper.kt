package me.intuit.cat.data.mapper

import me.intuit.cat.data.local.entity.BreedEntity
import me.intuit.cat.data.local.entity.BreedImageEntity
import me.intuit.cat.data.local.entity.toDomain

import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.model.BreedImage

fun Breed.toBreedEntity(): BreedEntity {
    return BreedEntity(
        rid = 0,
        id = id,
        description = description,
        origin = origin,
        temperament = temperament,
        name = name,
        hypoallergenic = hypoallergenic,
        wikipediaurl = wikipedia_url
    )
}

fun BreedImage.toBreedImageEntity(): BreedImageEntity {
    return BreedImageEntity(
        rid= 0,
        id = id,
        url = url,
    )
}
fun List<BreedImage>.toBreedEntityList(): List<BreedImageEntity> {
    return this.map {
        it.toBreedImageEntity()
    }
}

fun List<BreedImageEntity>.toBreedImageList(): List<BreedImage> {
    return this.map {
        it.toDomain()
    }
}


fun List<Breed>.toEntityList(): List<BreedEntity> {
    return this.map {
        it.toBreedEntity()
    }
}

fun List<BreedEntity>.toDomainList(): List<Breed> {
    return this.map {
        it.toDomain()
    }
}
/*


fun CatImageListItemDTO.mapFromEntity() = CatImageListItem(
    id = this.id,
    height = this.height,
    url = this.url,
    width = this.width,
)

fun CatImageListItem.mapFromDomain() = CatImageListItemDTO(
    id = this.id,
     height = this.height,
     url = this.url,
     width = this.width,
)

fun List<CatImageListItemDTO>.mapFromListModel(): List<CatImageListItem> {
    return this.map {
        it.mapFromEntity()
    }
}

fun List<CatImageListItem>.mapFromListDomain(): List<CatImageListItemDTO> {
    return this.map {
        it.mapFromDomain()
    }
}
fun CatBreedDetailsDTO.mapFromEntity() = CatBreedDetails(
    breeds = breeds.mapFromBreedListModel(),
    id = id,
    url =  url,
)
*/
/* fun CatBreedDetails.mapFromDomain() =  CatBreedDetailsDTO(
    breeds = breeds.mapFromDomain(),
     id = id,
    url =  url,
)*//*

fun BreedDTO.mapFromEntity() = Breed(
    alt_names,
    description,
    id,
    name,
    wikipedia_url,
    description)

fun Breed.mapFromDomain() =  BreedDTO(
    alt_names =  alt_names,
    description= description,
    id=  id,
    name= name,
    wikipedia_url = wikipedia_url
)

fun List<Breed>.mapFromDomain():List<BreedDTO>{
    return this.map {
        it.mapFromDomain()
    }
}

fun List<BreedDTO>.mapFromBreedListModel(): List<Breed> {
    return this.map {
        it.mapFromEntity()
    }
}
*/
