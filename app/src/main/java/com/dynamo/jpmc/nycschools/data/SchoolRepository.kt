package com.dynamo.jpmc.nycschools.data

import com.dynamo.jpmc.nycschools.BuildConfig
import com.dynamo.jpmc.nycschools.data.local.SchoolDatabase
import com.dynamo.jpmc.nycschools.data.model.SatScores
import com.dynamo.jpmc.nycschools.data.model.SchoolDetail
import com.dynamo.jpmc.nycschools.util.SchoolApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by tanmaythakar on 3/17/23.
 */
class SchoolRepository @Inject constructor(val api: SchoolApi, val db: SchoolDatabase) {

    /**
     * If the @param hardRefresh = true then it will always fetch the result from API
     * but if it's @param hardRefresh = false it will first try to get the local data if it has
     */
    suspend fun getSchoolDetails(hardRefresh: Boolean): Response<List<SchoolDetail>> {
        return withContext(Dispatchers.IO) {
            val dbResults = db.schoolDao().getAllSchool()
            if (!hardRefresh && dbResults.isNotEmpty()) {
                Response.Success(dbResults)
            } else {
                try {
                    val response = api.getSchoolDetails(BuildConfig.NYC_API_KEY)
                    if (response.isSuccessful) {
                        val data = response.body()
                        data?.let { it ->
                            db.schoolDao().insertAll(it)
                        }
                        Response.Success(db.schoolDao().getAllSchool())
                    } else {
                        Response.Error(response.message())
                    }
                } catch (e: Exception) {
                    if (dbResults.isEmpty()) Response.Error(e.message) else Response.Success(
                        dbResults
                    )
                }
            }
        }
    }

    /**
     * If the @param hardRefresh = true then it will always fetch the result from API
     * but if it's @param hardRefresh = false it will first try to get the local data if it has
     * @param dbn = unique school Id id to get the sat scores
     */
    suspend fun getSatScores(hardRefresh: Boolean, dbn: String): Response<SatScores> {
        return withContext(Dispatchers.IO) {
            val dbResults = db.satScoreDao().getSatScores(dbn)
            if (!hardRefresh && dbResults.isNotEmpty()) {
                Response.Success(dbResults[0])
            } else {
                try {
                    val response = api.getSatScore(dbn, BuildConfig.NYC_API_KEY)
                    if (response.isSuccessful) {
                        val data = response.body()
                        data?.let {
                            db.satScoreDao().insert(data[0])
                        }
                    }
                    Response.Success(db.satScoreDao().getSatScores(dbn)[0])
                } catch (e: Exception) {
                    if (dbResults.isEmpty()) Response.Error(e.message) else Response.Success(
                        dbResults[0]
                    )
                }
            }
        }
    }
}