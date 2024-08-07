package me.intuit.cat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.intuit.cat.data.local.entity.BreedRemoteKeyDbData

/**
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
    @Query("Select created_at From breed_remote_keys Order By created_at DESC LIMIT 1")
     fun getCreationTime(): Long?
}