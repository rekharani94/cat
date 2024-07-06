package me.intuit.cat.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "breed_remote_keys")
data class BreedRemoteKeyDbData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "prevKey")
    var prevPage: Int?=0,
    @ColumnInfo(name = "nextKey")
    var nextPage: Int?=0,
    @ColumnInfo(name = "created_at")
var createdAt: Long = System.currentTimeMillis()
)