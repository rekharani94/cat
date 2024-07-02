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
        imageId =imageId,
        description = description,
        origin = origin,
        temperament = temperament,
        name = name,
        hypoallergenic = hypoallergenic,
        wikipediaurl = wikipedia_url
    )
}

fun BreedImage.toBreedImageEntity(page:Int): BreedImageEntity {
    return BreedImageEntity(
        rid= 0,
        id = id,
        url = url,
        breeds = breeds,
        timestamp = System.currentTimeMillis(),
        page=page
    )
}
fun List<BreedImage>.toBreedEntityList(page:Int): List<BreedImageEntity> {
    return this.map {
        it.toBreedImageEntity(page)
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



