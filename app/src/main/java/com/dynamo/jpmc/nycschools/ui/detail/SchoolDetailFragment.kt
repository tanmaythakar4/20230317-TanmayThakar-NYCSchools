package com.dynamo.jpmc.nycschools.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.dynamo.jpmc.nycschools.R
import com.dynamo.jpmc.nycschools.data.Response
import com.dynamo.jpmc.nycschools.data.model.SchoolDetail
import com.dynamo.jpmc.nycschools.databinding.FragmentSchoolDetailBinding
import com.dynamo.jpmc.nycschools.ui.SchoolViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by tanmaythakar on 3/18/23.
 */
@AndroidEntryPoint
class SchoolDetailFragment : Fragment(R.layout.fragment_school_detail) {

    lateinit var binding: FragmentSchoolDetailBinding
    private val schoolViewModel: SchoolViewModel by activityViewModels()
    val args: SchoolDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSchoolDetailBinding.bind(view)
        getSelectedSchoolDetail(args.schoolDetail)
    }

    private fun getSelectedSchoolDetail(schoolDetail: SchoolDetail?) {

        schoolDetail?.let {
            binding.schoolDetails = it
            schoolViewModel.getSatScores(it.dbn, false)
            binding.phoneButton.setOnClickListener { _ ->
                it.phone_number?.let { phoneNumber ->
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:$phoneNumber")
                    handleOutsideIntent(intent)
                }
            }
            binding.websiteButton.setOnClickListener { _ ->
                it.website?.let { it ->
                    var website = if (it.contains("http")) {
                        it
                    } else {
                        "https://$it"
                    }
                    handleOutsideIntent(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(website)
                        )
                    )
                }
            }
            binding.emailButton.setOnClickListener { _ ->
                it.school_email?.let { email ->
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "plain/text"
                    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                    handleOutsideIntent(intent)
                }

            }
            binding.mapButton.setOnClickListener { v ->
                val uri = "geo:${it.latitude},${it.longitude}" +
                        "?q=${it.school_name},${it.primary_address_line_1}," +
                        "${it.city} ,${it.state_code}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                intent.setPackage("com.google.android.apps.maps")
                handleOutsideIntent(intent)
            }

        }

        schoolViewModel.satScores.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Response.InProgress -> {
                    binding.satBodyCL.isVisible = false
                }
                is Response.Success -> {
                    binding.satBodyCL.isVisible = true
                    binding.satNoResultBodyCL.isVisible = false
                    binding.satScores = result.data
                }
                is Response.Error -> {
                    binding.satNoResultBodyCL.isVisible = true
                }
            }
        }

    }

    private fun handleOutsideIntent(intent: Intent) {
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            requireActivity().startActivity(intent);
        } else {
            Toast.makeText(requireActivity(), "Failed to open", Toast.LENGTH_SHORT).show();
        }

    }
}