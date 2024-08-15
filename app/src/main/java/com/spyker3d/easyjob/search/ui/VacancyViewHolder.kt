package com.spyker3d.easyjob.search.ui

import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.spyker3d.easyjob.R
import com.spyker3d.easyjob.databinding.JobListItemBinding
import com.spyker3d.easyjob.search.domain.model.Vacancy
import com.spyker3d.easyjob.utils.CurrencySymbol
import com.spyker3d.easyjob.utils.formatSalaryAmount

private const val CORNER = 12f

class VacancyViewHolder(private val binding: JobListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(vacancy: Vacancy) {
        binding.secretView.visibility = View.GONE
        binding.jobTitle.text = jobTitleText(vacancy)
        binding.jobEmployer.text = vacancy.employer?.name ?: ""
        binding.jobSalary.text = jobSalaryText(vacancy)
        Glide.with(itemView)
            .load(vacancy.employer?.logoUrls?.size90)
            .placeholder(R.drawable.placeholder_logo)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(itemView, CORNER)))
            .into(binding.jobImage)
    }

    fun bindFirst(vacancy: Vacancy) {
        binding.secretView.visibility = View.VISIBLE
        binding.jobTitle.text = jobTitleText(vacancy)
        binding.jobEmployer.text = vacancy.employer?.name ?: ""
        binding.jobSalary.text = jobSalaryText(vacancy)
        Glide.with(itemView)
            .load(vacancy.employer?.logoUrls?.size90)
            .placeholder(R.drawable.placeholder_logo)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(itemView, CORNER)))
            .into(binding.jobImage)
    }

    private fun jobTitleText(vacancy: Vacancy): String {
        return if (vacancy.area?.name == null) {
            vacancy.name
        } else {
            "${vacancy.name}, ${vacancy.area.name}"
        }
    }

    private fun jobSalaryText(vacancy: Vacancy): String {
        val text = when {
            vacancy.salary == null -> binding.root.resources.getString(R.string.no_salary_msg)
            vacancy.salary.from == null && vacancy.salary.to != null -> {
                binding.root.resources.getString(
                    R.string.salary_to,
                    formatSalaryAmount(vacancy.salary.to),
                    CurrencySymbol.getCurrencySymbol(vacancy.salary.currency)
                )
            }

            vacancy.salary.to == null -> {
                binding.root.resources.getString(
                    R.string.salary_from,
                    formatSalaryAmount(vacancy.salary.from),
                    CurrencySymbol.getCurrencySymbol(vacancy.salary.currency)
                )
            }

            else -> {
                binding.root.resources.getString(
                    R.string.salary_range,
                    formatSalaryAmount(vacancy.salary.from),
                    formatSalaryAmount(vacancy.salary.to),
                    CurrencySymbol.getCurrencySymbol(vacancy.salary.currency)
                )
            }
        }
        return text
    }

    private fun dpToPx(view: View, dp: Float): Int {
        val displayMetrics = view.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)
            .toInt()
    }
}
