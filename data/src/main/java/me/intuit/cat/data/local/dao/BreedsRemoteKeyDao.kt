package me.intuit.cat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.intuit.cat.data.local.entity.BreedRemoteKeyDbData

/**
 * @author by Ali Asadi on 30/01/2023
 */
@Dao
interface BreedsRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun saveRemoteKey(keys: BreedRemoteKeyDbData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(Keys: List<BreedRemoteKeyDbData>)

    @Query("SELECT * FROM breed_remote_keys WHERE id=:id")
     fun getRemoteKeyByMovieId(id: Int): BreedRemoteKeyDbData

    @Query("DELETE FROM breed_remote_keys")
     fun clearRemoteKeys()

    @Query("SELECT * FROM breed_remote_keys WHERE id = (SELECT MAX(id) FROM breed_remote_keys)")
     fun getLastRemoteKey(): BreedRemoteKeyDbData
}