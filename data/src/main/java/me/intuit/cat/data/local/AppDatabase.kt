package me.intuit.cat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.intuit.cat.data.local.dao.BreedDao
import me.intuit.cat.data.local.dao.BreedImageDao
import me.intuit.cat.data.local.dao.BreedsRemoteKeyDao
import me.intuit.cat.data.local.entity.BreedEntity
import me.intuit.cat.data.local.entity.BreedImageEntity
import me.intuit.cat.data.local.entity.BreedRemoteKeyDbData
import me.intuit.cat.data.local.entity.BreedTypeConverters

@Database(entities = [BreedImageEntity::class, BreedEntity::class, BreedRemoteKeyDbData::class], version = 10, exportSchema = false)
@TypeConverters(value = [BreedTypeConverters::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedImageDao(): BreedImageDao
    abstract fun breedDao(): BreedDao
    abstract fun breedsRemoteKeyDao(): BreedsRemoteKeyDao
    //abstract fun breedDetailsDao(): BreedDetailDao
}