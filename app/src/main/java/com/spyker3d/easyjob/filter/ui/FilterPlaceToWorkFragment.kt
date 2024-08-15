package com.spyker3d.easyjob.filter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.spyker3d.easyjob.R
import com.spyker3d.easyjob.databinding.FragmentFilterSettingsBinding
import com.spyker3d.easyjob.filter.presentation.state.PlaceToWorkFilterState
import com.spyker3d.easyjob.filter.presentation.viewmodel.PlaceToWorkFilterViewModel

class FilterPlaceToWorkFragment : Fragment() {
    private var _binding: FragmentFilterSettingsBinding? = null
    private val binding get() = _binding!!
    private var applyButtonWasClicked = false

    private val viewModel: PlaceToWorkFilterViewModel by viewModel<PlaceToWorkFilterViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilterSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateFragment()

        viewModel.getCurrentFilterAreaParameters()

        viewModel.stateLiveData.observe(viewLifecycleOwner) {
            renderFilterFields(it)
        }

        binding.filterArrowBack.setOnClickListener {
            viewModel.clearArea()
            viewModel.clearCountry()
            findNavController().navigateUp()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (!applyButtonWasClicked) {
                viewModel.clearArea()
                viewModel.clearCountry()
            }
            findNavController().navigateUp()
        }

        binding.filterWorkPlaceInactive.setOnClickListener {
            findNavController().navigate(R.id.action_filterPlaceToWorkFragment_to_filterCountryFragment)
        }

        binding.filterWorkPlaceActive.setOnClickListener {
            findNavController().navigate(R.id.action_filterPlaceToWorkFragment_to_filterCountryFragment)
        }

        binding.filterIndustryInactive.setOnClickListener {
            findNavController().navigate(R.id.action_filterPlaceToWorkFragment_to_filterRegionFragment)
        }

        binding.filterIndustryActive.setOnClickListener {
            findNavController().navigate(R.id.action_filterPlaceToWorkFragment_to_filterRegionFragment)
        }

        binding.filterWorkPlaceCross.setOnClickListener { viewModel.clearCountry() }
        binding.filterIndustryCross.setOnClickListener { viewModel.clearArea() }

        binding.filterApplyButton.setOnClickListener {
            viewModel.saveFilterAreaParameters()
            applyButtonWasClicked = true
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun renderFilterFields(filterParamsState: PlaceToWorkFilterState?) {
        if (filterParamsState is PlaceToWorkFilterState.AreaFilter) {
            if (!filterParamsState.countryName.isNullOrEmpty()) {
                binding.filterWorkPlaceInactive.isVisible = false
                binding.filterWorkPlaceActive.isVisible = true
                binding.filterWorkPlaceTitle.text = requireActivity().getString(R.string.country)
                binding.filterWorkPlaceValue.text = filterParamsState.countryName

            } else {
                binding.filterWorkPlaceInactive.isVisible = true
                binding.filterWorkPlaceActive.isVisible = false
            }

            if (!filterParamsState.areaName.isNullOrEmpty()) {
                binding.filterIndustryInactive.isVisible = false
                binding.filterIndustryActive.isVisible = true
                binding.filterIndustryTitle.text = requireActivity().getString(R.string.region)
                binding.filterIndustryValue.text = filterParamsState.areaName
            } else {
                binding.filterIndustryInactive.isVisible = true
                binding.filterIndustryActive.isVisible = false
            }
            binding.filterApplyButton.isVisible = !binding.filterWorkPlaceInactive.isVisible
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun inflateFragment() {
        binding.filterApplyButton.text = activity?.getString(R.string.select)
        binding.filterSalaryInput.visibility = View.GONE
        binding.filterSalaryInputTitle.visibility = View.GONE
        binding.filterSalaryInputBackground.visibility = View.GONE
        binding.filterDontShowWithoutSalaryCheckBox.visibility = View.GONE
        binding.filterDontShowWithoutSalaryTitle.visibility = View.GONE
        binding.filterApplyButton.visibility = View.GONE
        binding.filterResetButton.visibility = View.GONE

        binding.filterTitle.text = requireActivity().getString(R.string.choice_place_to_work)
        binding.filterFirstFilterName.text = requireActivity().getString(R.string.country)
        binding.filterSecondFilterName.text = requireActivity().getString(R.string.region)
    }

}
