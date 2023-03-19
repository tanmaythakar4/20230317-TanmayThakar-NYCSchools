package com.dynamo.jpmc.nycschools.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dynamo.jpmc.nycschools.data.Response
import com.dynamo.jpmc.nycschools.data.SchoolRepository
import com.dynamo.jpmc.nycschools.data.model.SatScores
import com.dynamo.jpmc.nycschools.data.model.SchoolDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tanmaythakar on 3/18/23.
 */

@HiltViewModel
class SchoolViewModel @Inject constructor(
    private val schoolRepository: SchoolRepository
) : ViewModel() {

    private val _schoolDetails = MutableLiveData<Response<List<SchoolDetail>>>()
    private val _satScores = MutableLiveData<Response<SatScores>>()
    val schoolDetails: LiveData<Response<List<SchoolDetail>>>
        get() = _schoolDetails
    val satScores: LiveData<Response<SatScores>>
        get() = _satScores

    init {
        listSchoolDetails(false)
    }

    fun listSchoolDetails(hardRefresh: Boolean) {
        viewModelScope.launch {
            _schoolDetails.postValue(Response.InProgress())
            _schoolDetails.postValue(schoolRepository.getSchoolDetails(hardRefresh))
        }
    }

    fun getSatScores(dbn: String, hardRefresh: Boolean) {
        _satScores.postValue(Response.InProgress())
        viewModelScope.launch {
            _satScores.postValue(schoolRepository.getSatScores(hardRefresh, dbn))
        }
    }
}