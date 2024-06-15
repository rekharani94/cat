package me.intuit.cat.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.model.BreedDetails
import java.io.Serializable

@Entity(tableName = "breed_details")
data class BreedDetailEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rid")val rid: Int,
    @ColumnInfo(name = "id")val id: String,
    @ColumnInfo(name = "url")val url: String,
    @ColumnInfo(name = "breeds") val breeds: List<Breed>,

    ): Serializable
fun BreedDetailEntity.toDomain() = BreedDetails(
    id = id,
    url = url,
    breeds = breeds
)