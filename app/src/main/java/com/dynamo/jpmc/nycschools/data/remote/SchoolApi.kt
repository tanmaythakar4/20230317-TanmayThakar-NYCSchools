package com.dynamo.jpmc.nycschools.util

import com.dynamo.jpmc.nycschools.data.model.SatScores
import com.dynamo.jpmc.nycschools.data.model.SchoolDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created by tanmaythakar on 3/17/23.
 */

interface SchoolApi {
    @GET(ApiConstants.SCHOOL_LIST_ENDPOINT)
    suspend fun getSchoolDetails(@Header("X-App-Token") apiKey: String): Response<List<SchoolDetail>>

    @GET(ApiConstants.SAT_SCORE_ENDPOINT)
    suspend fun getSatScore(
        @Query("dbn") dbn: String,
        @Header("X-App-Token") apiKey: String
    ): Response<List<SatScores>>
}