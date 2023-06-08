package com.example.casoscovidus.ui.fragments

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.casoscovidus.R
import com.example.casoscovidus.databinding.FragmentListBinding
import com.example.casoscovidus.ui.adapters.ReportAdapter
import com.example.casoscovidus.utils.FetchingStatus
import com.example.casoscovidus.utils.FragmentType
import com.example.casoscovidus.utils.LoadingStatus
import com.example.casoscovidus.utils.toDateTime
import com.example.casoscovidus.viewmodels.ReportsViewModel

private const val FRAGMENT_TYPE = "fragment_type"

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: ReportsViewModel
    private lateinit var adapter: ReportAdapter
    private var fragmentType: FragmentType = FragmentType.ALL

    companion object {
        @JvmStatic
        fun newInstance(fragmentType: FragmentType): ListFragment {
            return ListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(FRAGMENT_TYPE, fragmentType)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViewModel()
        fragmentType =
            arguments?.getSerializable(FRAGMENT_TYPE) as? FragmentType ?: FragmentType.ALL
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        initObservers()
        initUI()
        initListeners()
        loadData()

        return binding.root
    }

    private fun initListeners() {
        binding.swlReports.setOnRefreshListener {
            fetchNewReports()
        }
    }

    private fun bindViewModel() {
        viewModel = ViewModelProvider(this)[ReportsViewModel::class.java]
    }

    private fun initObservers() {
        if (fragmentType == FragmentType.ALL) {
            viewModel.reports.observe(viewLifecycleOwner) { reports ->
                adapter.setData(reports ?: listOf())
            }
        } else if (fragmentType == FragmentType.FAVORITES) {
            viewModel.favorites.observe(viewLifecycleOwner) { favorites ->
                adapter.setData(favorites ?: listOf())
            }
        }

        viewModel.lastChecked.observe(viewLifecycleOwner) { lastChecked ->
            binding.tvLastUpdate.text =
                getString(R.string.last_update_msg, lastChecked.toDateTime())
        }

        viewModel.loadingStatus.observe(viewLifecycleOwner) { loadingStatus ->
            when (loadingStatus) {
                LoadingStatus.LOADING -> setProgressBarVisibility(View.VISIBLE)

                LoadingStatus.DONE -> setProgressBarVisibility(View.GONE)

                LoadingStatus.NO_INTERNET_CONNECTION -> {
                    showToast(R.string.no_internet_connection_msg)
                    setProgressBarVisibility(View.GONE)
                }

                LoadingStatus.ERROR -> {
                    showToast(R.string.unexpected_error_msg)
                    setProgressBarVisibility(View.GONE)
                }

                else -> {}
            }
        }

        viewModel.isDataEmpty.observe(viewLifecycleOwner) { isDataEmpty ->
            if (isDataEmpty) {
                setEmptyDataMsgVisibility(View.VISIBLE)
            } else {
                setEmptyDataMsgVisibility(View.GONE)
            }
        }
    }

    private fun initUI() {
        when (fragmentType) {
            FragmentType.ALL -> {
                binding.tvEmptyMsg.text = getString(R.string.no_reports_msg)
            }

            FragmentType.FAVORITES -> {
                binding.tvLastUpdate.visibility = View.GONE
                binding.tvEmptyMsg.text = getString(R.string.no_favorite_reports_msg)
            }
        }

        binding.swlReports.setColorSchemeColors(getPrimaryColor())
        // Settings for the recycler view and its adapter
        binding.rvReports.layoutManager = LinearLayoutManager(context)
        adapter = ReportAdapter(this, fragmentType)
        binding.rvReports.adapter = adapter
    }

    private fun getPrimaryColor(): Int {
        val typedValue = TypedValue()

        requireContext().theme.resolveAttribute(
            androidx.appcompat.R.attr.colorPrimary, typedValue, true
        )

        return typedValue.resourceId
    }

    // Handles fetching behavior when user has swiped the refresh layout in All fragment
    private fun fetchNewReports() {
        if (fragmentType != FragmentType.ALL) {
            binding.swlReports.isRefreshing = false
            return
        }

        viewModel.fetchingStatus.observe(viewLifecycleOwner) { fetchingStatus ->
            if (fetchingStatus != FetchingStatus.LOADING) {
                binding.swlReports.isRefreshing = false

                when (fetchingStatus) {
                    FetchingStatus.NO_INTERNET_CONNECTION -> showToast(R.string.no_internet_connection_msg)

                    FetchingStatus.ERROR -> showToast(R.string.unexpected_error_msg)

                    else -> {}
                }

                viewModel.fetchingStatus.removeObservers(viewLifecycleOwner)
            }
        }

        viewModel.fetchAngGetReports()
    }

    // Tells view model to retrieve data
    private fun loadData() {
        when (fragmentType) {
            FragmentType.ALL -> viewModel.loadReports()
            FragmentType.FAVORITES -> viewModel.loadFavorites()
        }
    }

    private fun setProgressBarVisibility(visibility: Int) {
        binding.pbLoading.visibility = visibility
    }

    private fun showToast(stringResourceId: Int) =
        Toast.makeText(context, getString(stringResourceId), Toast.LENGTH_LONG).show()

    private fun setEmptyDataMsgVisibility(visibility: Int) {
        binding.tvEmptyMsg.visibility = visibility
    }
}