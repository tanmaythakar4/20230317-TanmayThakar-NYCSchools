package com.dynamo.jpmc.nycschools.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dynamo.jpmc.nycschools.data.model.SchoolDetail
import com.dynamo.jpmc.nycschools.databinding.SchoolItemBinding

/**
 * Created by tanmaythakar on 3/17/23.
 */
class SchoolsAdapter(private val onClick: (SchoolDetail) -> Unit) :
    RecyclerView.Adapter<SchoolsAdapter.ViewHolder>() {

    private var schoolList: List<SchoolDetail> = emptyList()

    fun setData(list: List<SchoolDetail>) {
        schoolList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: SchoolItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(schoolDetail: SchoolDetail) {
            binding.detail = schoolDetail
            binding.schoolRowCL.setOnClickListener { onClick(schoolDetail) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SchoolItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return schoolList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(schoolList[position])
    }

}