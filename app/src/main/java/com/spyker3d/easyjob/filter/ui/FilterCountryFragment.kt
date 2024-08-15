package com.spyker3d.easyjob.filter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.spyker3d.easyjob.R
import com.spyker3d.easyjob.databinding.FragmentFilterWithRecyclerBinding
import com.spyker3d.easyjob.filter.domain.model.AreaDetailsFilterItem
import com.spyker3d.easyjob.filter.presentation.state.AreaFilterState
import com.spyker3d.easyjob.filter.presentation.viewmodel.CountryFilterViewModel

class FilterCountryFragment : Fragment() {
    private var _binding: FragmentFilterWithRecyclerBinding? = null
    private val binding get() = _binding!!

    private val viewModelCountry: CountryFilterViewModel by viewModel<CountryFilterViewModel>()

    private var adapter = FilterCountryAdapter(emptyList(), ::setItemOnClickListener)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilterWithRecyclerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchTitle.text = requireContext().getString(R.string.choice_country)
        setSearchViewVisibility()

        binding.backButtonFilterWithRecycler.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewModelCountry.stateLiveDataCountry.observe(viewLifecycleOwner) { renderUiElements(it) }

        viewModelCountry.backEvent.observe(viewLifecycleOwner) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderUiElements(state: AreaFilterState) {
        binding.searchPlaceholderImage.isVisible = state is AreaFilterState.Error ||
            state is AreaFilterState.InternetConnectionError || state is AreaFilterState.Empty
        binding.searchPlaceholderText.isVisible = state is AreaFilterState.Error ||
            state is AreaFilterState.InternetConnectionError || state is AreaFilterState.Empty
        binding.loadingProgressBar.isVisible = state is AreaFilterState.Loading
        binding.recyclerViewFilter.isVisible = state is AreaFilterState.AreaContent
        val state2 = state

        when (state) {
            is AreaFilterState.AreaContent -> {
                adapter.updateList(state.listOfAreas)
                binding.recyclerViewFilter.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.recyclerViewFilter.adapter = adapter
            }

            is AreaFilterState.Error, is AreaFilterState.Empty -> {
                binding.searchPlaceholderImage.setImageResource(R.drawable.picture_flying_men)
                binding.searchPlaceholderText.text =
                    requireContext().getString(R.string.failed_to_receive_region_list)
            }

            is AreaFilterState.Loading -> Unit

            is AreaFilterState.InternetConnectionError -> {
                binding.searchPlaceholderImage.setBackgroundResource(0)
                binding.searchPlaceholderImage.setImageResource(R.drawable.picture_funny_head)
                binding.searchPlaceholderText.text = requireContext().getString(R.string.no_internet)
            }
        }
    }

    private fun setItemOnClickListener(country: AreaDetailsFilterItem) {
        viewModelCountry.saveCountryChoiceToFilter(country)
    }

    private fun setSearchViewVisibility() {
        binding.filterApplyButton.isVisible = false
        binding.filterInput.isVisible = false
        binding.filterInputIcon.isVisible = false
    }
}
