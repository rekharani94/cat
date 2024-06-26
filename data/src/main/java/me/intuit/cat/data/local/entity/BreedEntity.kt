package me.intuit.cat.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.intuit.cat.domain.model.Breed
import java.io.Serializable

@Entity(tableName = "breed_entity")
data class BreedEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tid")val tid: Int =0,
    @ColumnInfo(name = "rid")val rid: Int,
    @ColumnInfo(name = "id")val id: String,
    @ColumnInfo(name = "imageId") var imageId: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "origin") val origin: String?,
    @ColumnInfo(name = "temperament") val temperament: String?,
    @ColumnInfo(name = "hypoallergenic") val hypoallergenic: Int,
    @ColumnInfo(name = "wikipediaurl") val wikipediaurl: String?,
) : Serializable
fun BreedEntity.toDomain() = Breed(
    id = id.toString(),
    imageId = id,
    name = name,
    description = description,
    origin = origin,
    temperament = temperament,
    hypoallergenic = hypoallergenic,
    wikipedia_url= wikipediaurl
)