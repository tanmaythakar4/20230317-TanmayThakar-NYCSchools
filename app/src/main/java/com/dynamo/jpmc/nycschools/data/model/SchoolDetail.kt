package com.dynamo.jpmc.nycschools.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "School")
@Parcelize
data class SchoolDetail(
    @PrimaryKey @ColumnInfo(name = "dbn") val dbn: String,
    @ColumnInfo(name = "school_name") val school_name: String?,
    @ColumnInfo(name = "campus_name") val campus_name: String?,
    @ColumnInfo(name = "overview_paragraph") val overview_paragraph: String?,
    @ColumnInfo(name = "start_time") val start_time: String?,
    @ColumnInfo(name = "end_time") val end_time: String?,
    @ColumnInfo(name = "primary_address_line_1") val primary_address_line_1: String?,
    @ColumnInfo(name = "state_code") val state_code: String?,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "latitude") val latitude: String?,
    @ColumnInfo(name = "location") val location: String?,
    @ColumnInfo(name = "longitude") val longitude: String?,
    @ColumnInfo(name = "zip") val zip: String?,
    @ColumnInfo(name = "neighborhood") val neighborhood: String?,
    @ColumnInfo(name = "school_email") val school_email: String?,
    @ColumnInfo(name = "phone_number") val phone_number: String?,
    @ColumnInfo(name = "website") val website: String?,
    @ColumnInfo(name = "extracurricular_activities") val extracurricular_activities: String?,
    @ColumnInfo(name = "fax_number") val fax_number: String?,
    @ColumnInfo(name = "school_sports") val school_sports: String?,
    @ColumnInfo(name = "specialized") val specialized: String?,
    @ColumnInfo(name = "total_students") val total_students: String?
) : Parcelable