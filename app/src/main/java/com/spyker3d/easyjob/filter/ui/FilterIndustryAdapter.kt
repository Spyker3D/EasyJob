package com.spyker3d.easyjob.filter.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spyker3d.easyjob.databinding.FilterWithRecyclerItemBinding
import com.spyker3d.easyjob.filter.presentation.model.IndustryWithCheck

class FilterIndustryAdapter(
    private var listIndustry: List<IndustryWithCheck>,
    private var clickListener: (industry: IndustryWithCheck) -> Unit
) : RecyclerView.Adapter<FilterIndustryAdapter.IndustryFilterViewHolder>() {
    private var notFilteredList: List<IndustryWithCheck> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryFilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FilterWithRecyclerItemBinding.inflate(inflater, parent, false)
        return IndustryFilterViewHolder(binding, ::updateCheck)
    }

    override fun onBindViewHolder(
        holder: IndustryFilterViewHolder,
        position: Int
    ) {
        holder.bind(listIndustry[position])
        holder.itemView.setOnClickListener {
            updateCheck(listIndustry[position])
        }
    }

    private fun updateCheck(selectedIndustry: IndustryWithCheck) {
        val index = listIndustry.indexOf(selectedIndustry)
        clickListener(listIndustry[index])
        resetChecks(listIndustry[index])
    }

    private fun resetChecks(selectedIndustry: IndustryWithCheck) {
        val newList: MutableList<IndustryWithCheck> = mutableListOf()
        val savedIndex: MutableList<Int> = mutableListOf()
        listIndustry.forEachIndexed { index, industry ->
            if (industry.industry.id != selectedIndustry.industry.id && industry.isChecked) {
                newList.add(IndustryWithCheck(industry = industry.industry))
                savedIndex.add(index)
            } else if (industry.industry.id == selectedIndustry.industry.id) {
                newList.add(IndustryWithCheck(industry = industry.industry, isChecked = true))
                savedIndex.add(index)
            } else {
                newList.add(industry)
            }
        }
        savedIndex.forEach { index ->
            updateListRenderByIndex(newList, index)
        }
    }

    private fun updateListRenderByIndex(updatedIndustryList: List<IndustryWithCheck>, index: Int) {
        listIndustry = updatedIndustryList
        notFilteredList = updatedIndustryList
        notifyItemChanged(index)
    }

    fun updateList(updatedIndustryList: List<IndustryWithCheck>) {
        listIndustry = updatedIndustryList
        notFilteredList = updatedIndustryList
        notifyDataSetChanged()
    }

    fun updateListByFilter(filter: String) {
        listIndustry = notFilteredList.filter { industry ->
            industry.industry.name.uppercase().contains(filter.uppercase()) || filter.isEmpty()
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listIndustry.size
    }

    inner class IndustryFilterViewHolder(
        private val binding: FilterWithRecyclerItemBinding,
        private var updateCheck: (selectedIndustry: IndustryWithCheck) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: IndustryWithCheck) {
            binding.textViewFilterItem.text = item.industry.name
            binding.switcherFilter.isChecked = item.isChecked
            binding.switcherFilter.visibility = View.VISIBLE
            binding.arrowFilterMini.visibility = View.GONE
            binding.switcherFilter.setOnClickListener {
                updateCheck(item)
            }
        }
    }
}
