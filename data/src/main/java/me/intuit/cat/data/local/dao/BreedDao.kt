package me.intuit.cat.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import me.intuit.cat.data.local.entity.BreedEntity

/**
 */
@Dao
interface BreedDao {

    @Query("SELECT * FROM breed_entity ")
    fun getAll(): List<BreedEntity>

    @Query("SELECT * FROM breed_entity where imageId = :imgid")
    fun getBreedById(imgid:String): BreedEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
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