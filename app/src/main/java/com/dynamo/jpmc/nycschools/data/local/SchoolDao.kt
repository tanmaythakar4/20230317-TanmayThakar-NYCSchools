package com.dynamo.jpmc.nycschools.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dynamo.jpmc.nycschools.data.model.SchoolDetail

@Dao
interface SchoolDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(schoolDetail: SchoolDetail): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(schoolDetails: List<SchoolDetail>)

    @Query("SELECT * FROM School")
    suspend fun getAllSchool(): List<SchoolDetail>

    @Query("DELETE FROM School")
    suspend fun deleteAllSchool()
}
