package me.intuit.cat.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import me.intuit.cat.data.local.entity.BreedEntity

/**
 * data access object for currency entity
 */
@Dao
interface BreedDao {

    @Query("SELECT * FROM breed_entity ")
    fun breeds(): PagingSource<Int, BreedEntity>
    @Query("SELECT * FROM breed_entity ")
    fun getAll(): List<BreedEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(breed: BreedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(breeds: Collection<BreedEntity>)

    @Delete
    fun delete(breed: BreedEntity)

    @Query("DELETE FROM breed_entity")
    fun deleteAll()

    @Transaction
    fun deleteAllAndInsertAll(breeds: List<BreedEntity>) {
        deleteAll()
        return insertAll(breeds)
    }
}