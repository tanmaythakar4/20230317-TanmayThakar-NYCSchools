package com.dynamo.jpmc.nycschools.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SatScore")
data class SatScores(
    @PrimaryKey @ColumnInfo(name = "dbn") val dbn: String,
    @ColumnInfo(name = "num_of_sat_test_takers") val num_of_sat_test_takers: String,
    @ColumnInfo(name = "sat_critical_reading_avg_score") val sat_critical_reading_avg_score: String,
    @ColumnInfo(name = "sat_math_avg_score") val sat_math_avg_score: String,
    @ColumnInfo(name = "sat_writing_avg_score") val sat_writing_avg_score: String,
    @ColumnInfo(name = "school_name") val school_name: String
)