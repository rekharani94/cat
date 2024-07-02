package me.intuit.cat.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.intuit.cat.data.local.dao.BreedDao
import me.intuit.cat.data.local.dao.BreedImageDao
import me.intuit.cat.data.local.dao.BreedsRemoteKeyDao
import me.intuit.cat.data.local.entity.BreedEntity
import me.intuit.cat.data.local.entity.BreedImageEntity
import me.intuit.cat.data.local.entity.BreedRemoteKeyDbData
import me.intuit.cat.data.local.entity.BreedTypeConverters

@Database(entities = [BreedImageEntity::class, BreedEntity::class, BreedRemoteKeyDbData::class], version = 15, exportSchema = false)
@TypeConverters(value = [BreedTypeConverters::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedImageDao(): BreedImageDao
    abstract fun breedDao(): BreedDao
    abstract fun breedsRemoteKeyDao(): BreedsRemoteKeyDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null


        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "cat_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}