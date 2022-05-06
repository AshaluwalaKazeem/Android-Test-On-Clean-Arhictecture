package com.example.punchandroidtest.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.punchandroidtest.data.db.dto.MarsEntity

@Dao
interface MarsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMars(marsEntity: MarsEntity): Long

    @Query("SELECT * FROM mars")
    suspend fun get(): List<MarsEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(marsList: List<MarsEntity>)

}