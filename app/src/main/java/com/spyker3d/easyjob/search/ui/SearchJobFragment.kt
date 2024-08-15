package com.spyker3d.easyjob.search.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.spyker3d.easyjob.R
import com.spyker3d.easyjob.databinding.FragmentSearchJobBinding
import com.spyker3d.easyjob.details.ui.JobDetailsFragment
import com.spyker3d.easyjob.search.domain.model.Vacancy
import com.spyker3d.easyjob.search.presentation.SearchFragmentState
import com.spyker3d.easyjob.search.presentation.SearchViewModel

class SearchJobFragment : Fragment() {
    private var _binding: FragmentSearchJobBinding? = null
    private val binding get() = _binding!!
    private var suggestionsAdapter: VacancyPositionSuggestsAdapter? = null
    private val viewModel by viewModel<SearchViewModel>()
    private val adapter = VacancyAdapter(emptyList(), clickListenerFun())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchJobBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewHolderInit()
        viewModel.fragmentStateLiveData().observe(viewLifecycleOwner) { searchState ->
            renderSearchState(searchState)
        }
        onScrollListener()
        viewModel.filterStateToObserve.observe(viewLifecycleOwner) { setFilterIcon(it) }
        binding.searchJobsCountButton.setOnClickListener {
            viewModel.searchImmediately(binding.searchInput.text.toString())
        }
        binding.searchFilterButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchJobFragment_to_filterSettingsFragment)
        }
        initSearchInputActions()
        binding.searchInputIcon.setOnClickListener {
            binding.searchInput.setText(String())
            viewModel.updateState(SearchFragmentState.NoTextInInputEditText)
        }
        suggestionsAdapter = VacancyPositionSuggestsAdapter(requireActivity(), binding.searchInput)
        binding.searchInput.setAdapter(suggestionsAdapter)
        viewModel.suggestionsLivaData.observe(viewLifecycleOwner) { renderSuggestions(it) }
    }

    private fun initSearchInputActions() {
        binding.searchInput.doOnTextChanged { text, _, _, _ ->
            viewModel.searchWithDebounce(text.toString())
            if (text.isNullOrEmpty()) {
                viewModel.stopAutoSearch()
                binding.searchInputIcon.background = requireActivity().getDrawable(R.drawable.icon_search)
            } else {
                binding.searchInputIcon.background = requireActivity().getDrawable(R.drawable.icon_cross)
                viewModel.getSuggestionsForSearch(text.toString())
                viewModel.currentPage = 0
            }
        }
        binding.searchInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.searchInput.showKeyboard(requireContext())
            }
        }
        binding.searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.searchInput.hideKeyboard(requireContext())
                viewModel.currentPage = 0
                viewModel.searchImmediately(binding.searchInput.text.toString())
            }
            false
        }
    }

    private fun renderSearchVacancy(searchState: SearchFragmentState.SearchVacancy) {
        setVisible(
            placeholderText = false,
            list = true,
            blueButton = true,
            progress = false,
            progressMini = false
        )
        if (searchState.isFirstPage) binding.recyclerViewSearch.scrollToPosition(0)
        adapter.updateList(searchState.searchVacancy)
        setBlueButtonText(searchState)
    }

    private fun renderLoading() {
        setVisible(
            placeholderText = false,
            list = false,
            blueButton = false,
            progress = true,
            progressMini = false
        )
    }

    private fun renderNoResult() {
        if (viewModel.currentPage != 0) {
            showToast(requireActivity().getString(R.string.toast_server_error))
            setVisible(
                placeholderText = false,
                list = true,
                blueButton = true,
                progress = false,
                progressMini = false
            )

        } else {
            binding.searchPlaceholderImage.background =
                requireActivity().getDrawable(R.drawable.picture_angry_cat)
            binding.searchJobsCountButton.text = requireActivity().getString(R.string.no_such_vacancies)
            binding.searchPlaceholderText.text =
                requireActivity().getString(R.string.failed_list_vacancy)
            setVisible(
                placeholderText = true,
                list = false,
                blueButton = true,
                progress = false,
                progressMini = false
            )
        }
    }

    private fun renderServerError() {
        if (viewModel.currentPage != 0) {
            showToast(requireActivity().getString(R.string.toast_no_internet))
            setVisible(
                placeholderText = false,
                list = true,
                blueButton = true,
                progress = false,
                progressMini = false
            )
        } else {
            binding.searchPlaceholderImage.background =
                requireActivity().getDrawable(R.drawable.picture_funny_head)
            binding.searchPlaceholderText.text =
                requireActivity().getString(R.string.no_internet)
            setVisible(
                placeholderText = true,
                list = false,
                blueButton = false,
                progress = false,
                progressMini = false
            )
        }
    }

    private fun renderNoTextInInputEditText() {
        binding.searchPlaceholderImage.background =
            requireActivity().getDrawable(R.drawable.picture_looking_man)
        setVisible(
            placeholderText = false,
            list = false,
            blueButton = false,
            progress = false,
            image = true,
            progressMini = false
        )
    }

    private fun renderLoadingNewPage() {
        setVisible(
            placeholderText = false,
            list = true,
            blueButton = true,
            progress = false,
            image = false,
            progressMini = true
        )
    }

    private fun renderSearchState(searchState: SearchFragmentState) {
        when (searchState) {
            is SearchFragmentState.SearchVacancy -> renderSearchVacancy(searchState)
            is SearchFragmentState.Loading -> renderLoading()
            is SearchFragmentState.NoResult -> renderNoResult()
            is SearchFragmentState.ServerError -> renderServerError()
            is SearchFragmentState.NoTextInInputEditText -> renderNoTextInInputEditText()
            is SearchFragmentState.LoadingNewPage -> renderLoadingNewPage()
        }
    }

    private fun renderSuggestions(incomeSuggestions: List<String>) {
        suggestionsAdapter?.applyDataSet(incomeSuggestions)
    }

    private fun setVisible(
        placeholderText: Boolean,
        list: Boolean,
        blueButton: Boolean,
        progress: Boolean,
        progressMini: Boolean,
        image: Boolean = placeholderText,
    ) {
        with(binding) {
            searchPlaceholderText.isVisible = placeholderText
            searchPlaceholderImage.isVisible = image
            recyclerViewSearch.isVisible = list
            searchJobsCountButton.isVisible = blueButton
            searchProgressBar.isVisible = progress
            searchMiniProgressBar.isVisible = progressMini
            if (progressMini) {
                recyclerViewSearch.smoothScrollToPosition(adapter.itemCount)
            }
        }
    }

    private fun setBlueButtonText(state: SearchFragmentState.SearchVacancy) {
        val pluralVacancy = resources.getQuantityString(
            R.plurals.plurals_vacancy,
            state.totalFoundVacancy
        )
        val foundVac =
            requireActivity().getString(
                R.string.found_x_vacancies,
                state.totalFoundVacancy.toString()
            )
        val text = " $foundVac $pluralVacancy"
        binding.searchJobsCountButton.text = text
    }

    private fun setFilterIcon(filterIsActive: Boolean) {
        binding.searchFilterButton.setImageDrawable(
            if (filterIsActive) {
                requireActivity().getDrawable(R.drawable.icon_filter_active)
            } else {
                requireActivity().getDrawable(R.drawable.icon_filter)
            }
        )
    }

    private fun clickListenerFun() = object : SearchRecyclerViewEvent {
        override fun onItemClick(vacancy: Vacancy) {
            if (viewModel.clickDebounce()) {
                findNavController().navigate(
                    R.id.action_searchJobFragment_to_jobDetailsFragment,
                    JobDetailsFragment.createArgs(vacancy.id)
                )
            }
        }
    }

    private fun viewHolderInit() {
        binding.recyclerViewSearch.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewSearch.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun View.showKeyboard(context: Context) {
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, 0)
    }

    private fun View.hideKeyboard(context: Context) {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)

    }

    override fun onResume() {
        super.onResume()
        viewModel.checkFilterStatus()
        doFilteredRepeatSequence()
    }

    private fun doFilteredRepeatSequence() {
        val repeatHandler = requireActivity()
        if (repeatHandler is SearchRepeatHandler) {
            if (repeatHandler.getRepeatBool()) viewModel.repeatSearch()
            repeatHandler.setRepeat(false)
        }
    }

    private fun onScrollListener() {
        binding.recyclerViewSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val pos =
                        (binding.recyclerViewSearch.layoutManager as LinearLayoutManager)
                            .findLastCompletelyVisibleItemPosition()
                    val itemsCount = adapter.itemCount
                    if (pos >= itemsCount - 1) {
                        binding.recyclerViewSearch.smoothScrollToPosition(adapter.itemCount)
                        viewModel.onLastItemReached()
                    }
                }
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
