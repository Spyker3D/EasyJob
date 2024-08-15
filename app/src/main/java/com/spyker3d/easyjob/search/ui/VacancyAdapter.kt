package com.spyker3d.easyjob.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spyker3d.easyjob.databinding.JobListItemBinding
import com.spyker3d.easyjob.search.domain.model.Vacancy

private const val TYPE_VACANCY = 1
private const val TYPE_PROGRESS_BAR = 2

class VacancyAdapter(
    vacancyList: List<Vacancy>,
    private val onVacancyClickListener: SearchRecyclerViewEvent
) : RecyclerView.Adapter<VacancyViewHolder>() {

    var vacancyList = vacancyList
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vacancyViewBinding = JobListItemBinding.inflate(inflater, parent, false)
        return VacancyViewHolder(vacancyViewBinding)

    }

    override fun getItemCount(): Int = vacancyList.size

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        if (position == 0) {
            holder.bindFirst(vacancyList[position])
        } else {
            holder.bind(vacancyList[position])
        }
        holder.itemView.setOnClickListener {
            onVacancyClickListener.onItemClick(vacancyList[holder.adapterPosition])
        }
    }

    fun updateList(updatedVacancyList: List<Vacancy>) {
        vacancyList = updatedVacancyList
        notifyDataSetChanged()
    }
}

interface SearchRecyclerViewEvent {
    fun onItemClick(vacancy: Vacancy)
}

