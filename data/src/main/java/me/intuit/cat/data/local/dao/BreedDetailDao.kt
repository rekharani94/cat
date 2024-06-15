package me.intuit.cat.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import me.intuit.cat.data.local.entity.BreedDetailEntity

/**
 * data access object for currency entity
 */
@Dao
interface BreedDetailDao {

    @Query("SELECT * FROM breed_details ")
    fun breeds(): PagingSource<Int, BreedDetailEntity>
    @Query("SELECT * FROM breed_details ")
    fun getAll(): List<BreedDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(breed: BreedDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(breeds: Collection<BreedDetailEntity>)

    @Delete
    fun delete(breed: BreedDetailEntity)

    @Query("DELETE FROM breed_entity")
    fun deleteAll()

    @Transaction
    fun deleteAllAndInsertAll(breeds: List<BreedDetailEntity>) {
        deleteAll()
        return insertAll(breeds)
    }
}