package com.dynamo.jpmc.nycschools.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dynamo.jpmc.nycschools.R
import com.dynamo.jpmc.nycschools.data.Response
import com.dynamo.jpmc.nycschools.databinding.FragmentSchoolListBinding
import com.dynamo.jpmc.nycschools.ui.SchoolViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by tanmaythakar on 3/17/23.
 */

@AndroidEntryPoint
class SchoolListFragment : Fragment(R.layout.fragment_school_list) {

    lateinit var binding: FragmentSchoolListBinding
    private val schoolViewModel: SchoolViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSchoolListBinding.bind(view)
        setupSchoolListAdapter()
        setupSwipeRefresh()
        getSchoolDetails()
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.errorMessage.isVisible = false
            schoolViewModel.listSchoolDetails(true)
        }
    }

    /**
     * setup and school list adapter and recyclerview
     */
    private fun setupSchoolListAdapter() {
        binding.schoolRecyclerView.apply {
            adapter =
                SchoolsAdapter() { schoolDetail ->
                    val action =
                        SchoolListFragmentDirections.actionSchoolListFragmentToSchoolDetailFragment(
                            schoolDetail
                        )
                    findNavController().navigate(action)
                }
            layoutManager = LinearLayoutManager(context)
        }
    }

    /**
     * This will observe the School Data
     */
    private fun getSchoolDetails() {
        schoolViewModel.schoolDetails.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Response.InProgress -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }
                is Response.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.schoolRecyclerView.isVisible = true
                    val adapter = binding.schoolRecyclerView.adapter as SchoolsAdapter
                    adapter.setData(result.data)
                }
                is Response.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.schoolRecyclerView.isVisible = false
                    binding.errorMessage.isVisible = true
                    binding.errorMessage.text = result.message
                }
            }
        }
    }
}