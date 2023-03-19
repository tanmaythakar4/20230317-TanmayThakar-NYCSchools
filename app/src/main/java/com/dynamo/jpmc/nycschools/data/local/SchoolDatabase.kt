package com.dynamo.jpmc.nycschools.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dynamo.jpmc.nycschools.data.model.SatScores
import com.dynamo.jpmc.nycschools.data.model.SchoolDetail

/**
 * Created by tanmaythakar on 3/17/23.
 */
@Database(
    version = 1,
    entities = [SchoolDetail::class, SatScores::class],
)
abstract class SchoolDatabase : RoomDatabase() {
    abstract fun schoolDao(): SchoolDao
    abstract fun satScoreDao(): SatScoreDao
}