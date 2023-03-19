package com.dynamo.jpmc.nycschools.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dynamo.jpmc.nycschools.data.Response
import com.dynamo.jpmc.nycschools.data.SchoolRepository
import com.dynamo.jpmc.nycschools.data.model.SchoolDetail
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

/**
 * Created by tanmaythakar on 3/19/23.
 */
class SchoolViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: SchoolViewModel
    private val repository = mockk<SchoolRepository>()

    private val listOfSchool = (0..2).map {
        SchoolDetail(
            "id-$it",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        coEvery {
            repository.getSchoolDetails(any())
        } returns Response.Success(listOfSchool)
        viewModel = SchoolViewModel(repository)
    }

    @Test
    fun `When Fetching school details correct state is pass`() {
        val values = mutableListOf<Response<List<SchoolDetail>>>()
        viewModel.schoolDetails.observeForever {
            values.add(it)
        }
        viewModel.listSchoolDetails(false)
        dispatcher.scheduler.advanceUntilIdle()
        assert(viewModel.schoolDetails.value is Response.Success)
        assert(values[0] is Response.InProgress)
        assert(values[1] is Response.Success)
    }

}