package com.spyker3d.easyjob.favorites.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spyker3d.easyjob.databinding.JobListItemBinding
import com.spyker3d.easyjob.details.domain.model.VacancyDetails

class VacancyDetailsRecyclerAdapter(
    vacancyDetailsList: List<VacancyDetails> = emptyList(),
    private val onVacancyDetailClickListener: ClickerForVacancyDetail,
) : RecyclerView.Adapter<VacancyDetailsViewHolder>() {
    var vacancies: List<VacancyDetails> = vacancyDetailsList
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
            return
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyDetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vacancyViewBinding = JobListItemBinding.inflate(inflater, parent, false)
        return VacancyDetailsViewHolder(vacancyViewBinding)
    }

    override fun onBindViewHolder(holder: VacancyDetailsViewHolder, position: Int) {
        holder.bind(vacancies[position], onVacancyDetailClickListener)
    }

    override fun getItemCount(): Int = vacancies.size
}
