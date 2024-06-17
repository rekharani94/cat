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

