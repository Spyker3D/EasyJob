package com.spyker3d.easyjob.filter.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spyker3d.easyjob.databinding.FilterWithRecyclerItemBinding
import com.spyker3d.easyjob.filter.domain.model.AreaDetailsFilterItem

class FilterCountryAdapter(
    private var listCountry: List<AreaDetailsFilterItem>,
    private var clickListener: (country: AreaDetailsFilterItem) -> Unit
) : RecyclerView.Adapter<FilterCountryAdapter.CountryFilterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryFilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vacancyViewBinding = FilterWithRecyclerItemBinding.inflate(inflater, parent, false)
        return CountryFilterViewHolder(vacancyViewBinding)
    }

    override fun onBindViewHolder(holder: CountryFilterViewHolder, position: Int) {
        holder.bind(listCountry[position])
        holder.itemView.setOnClickListener {
            clickListener(listCountry[position])
        }
    }

    fun updateList(updatedVacancyList: List<AreaDetailsFilterItem>) {
        listCountry = updatedVacancyList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listCountry.size
    }

    inner class CountryFilterViewHolder(private val binding: FilterWithRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AreaDetailsFilterItem) {
            binding.textViewFilterItem.text = item.areaName
            binding.switcherFilter.visibility = View.GONE
            binding.arrowFilterMini.visibility = View.VISIBLE
        }
    }
}
