package me.intuit.cat.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import me.intuit.cat.data.local.entity.BreedImageEntity

/**
 * data access object for currency entity
 */
@Dao
interface BreedImageDao {

    @Query("SELECT * FROM breed_image ")
    fun getAll(): List<BreedImageEntity>
    @Query("SELECT * FROM breed_image ")
    fun getAllImages(): PagingSource<Int, BreedImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(breed: BreedImageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(breeds: Collection<BreedImageEntity>)

    @Delete
    fun delete(breed: BreedImageEntity)

    @Query("DELETE FROM breed_image")
    fun deleteAll()

    @Transaction
    fun deleteAllAndInsertAll(breeds: List<BreedImageEntity>) {
        deleteAll()
        return insertAll(breeds)
    }
}