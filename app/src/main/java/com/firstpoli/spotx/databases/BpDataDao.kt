package com.firstpoli.spotx.databases

import androidx.room.*
import com.firstpoli.spotx.models.BpData

@Dao
interface BpDataDao {

    @Insert
    suspend fun insert(list: List<BpData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(BpData: BpData): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(BpData: BpData): Int

    @Delete
    suspend fun delete(BpData: BpData)

    @Delete
    suspend fun delete(BpData: List<BpData>)

    @Query("DELETE FROM ${AppDatabase.T_BpData} where id=:id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM ${AppDatabase.T_BpData}")
    suspend fun deleteAll()

    @Query("SELECT * FROM ${AppDatabase.T_BpData} where id=:id")
    fun queryById(id: Int): BpData?

    @Query("SELECT * FROM ${AppDatabase.T_BpData}")
    suspend fun queryAll(): List<BpData>

}