package com.example.punchandroidtest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.punchandroidtest.data.db.dto.MarsEntity

@Database(entities = [MarsEntity::class], version = 1)
abstract class MarsDatabase : RoomDatabase() {

    abstract fun marsDao(): MarsDao

    companion object {
        const val DATABASE_NAME = "mars_db"
    }
}