package com.example.punchandroidtest.data.db

import androidx.room.*
import com.example.punchandroidtest.data.db.dto.MarsEntity

@Dao
interface MarsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMars(marsEntity: MarsEntity): Long

    @Query("SELECT * FROM mars")
    suspend fun get(): List<MarsEntity>


    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(marsList: List<MarsEntity>)

    @Update
    suspend fun updateDb(marsEntity: MarsEntity)
}