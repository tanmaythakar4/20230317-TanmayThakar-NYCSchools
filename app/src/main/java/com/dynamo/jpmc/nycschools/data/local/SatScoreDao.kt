package com.dynamo.jpmc.nycschools.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dynamo.jpmc.nycschools.data.model.SatScores

@Dao
interface SatScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(satScores: SatScores): Long

    @Query("SELECT * FROM SatScore")
    suspend fun getAllSatScores(): List<SatScores>

    @Query("SELECT * FROM SatScore WHERE dbn= :dbn")
    suspend fun getSatScores(dbn:String): List<SatScores>

    @Query("DELETE FROM SatScore")
    suspend fun deleteAllSatScores()
}
