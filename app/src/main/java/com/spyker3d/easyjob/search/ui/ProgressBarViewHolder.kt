package com.spyker3d.easyjob.search.ui

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.spyker3d.easyjob.databinding.ProgressBarRecyclerItemBinding

class ProgressBarViewHolder(private val binding: ProgressBarRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(isLastPage: Boolean) {
        binding.loadingProgressBar.isVisible = !isLastPage
    }
}
