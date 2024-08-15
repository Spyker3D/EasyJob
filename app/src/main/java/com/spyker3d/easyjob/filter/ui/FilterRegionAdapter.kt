package com.spyker3d.easyjob.filter.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spyker3d.easyjob.databinding.FilterWithRecyclerItemBinding
import com.spyker3d.easyjob.filter.domain.model.Area

class FilterRegionAdapter(
    private var listArea: List<Area>,
    private var clickListener: (area: Area) -> Unit
) : RecyclerView.Adapter<FilterRegionAdapter.RegionFilterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionFilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FilterWithRecyclerItemBinding.inflate(inflater, parent, false)
        return RegionFilterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RegionFilterViewHolder,
        position: Int
    ) {
        holder.bind(listArea[position])
        holder.itemView.setOnClickListener {
            clickListener(listArea[position])
        }
    }

    fun updateList(updatedVacancyList: List<Area>) {
        listArea = updatedVacancyList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listArea.size
    }

    inner class RegionFilterViewHolder(
        private val binding: FilterWithRecyclerItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Area) {
            binding.textViewFilterItem.text = item.name
            binding.switcherFilter.visibility = View.GONE
            binding.arrowFilterMini.visibility = View.VISIBLE
        }
    }
}
