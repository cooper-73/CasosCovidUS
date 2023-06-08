package com.example.casoscovidus.ui.fragments

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.casoscovidus.R
import com.example.casoscovidus.adapters.ReportAdapter
import com.example.casoscovidus.databinding.FragmentListBinding
import com.example.casoscovidus.utils.toDateTime
import com.example.casoscovidus.viewmodels.ReportsViewModel

private const val IS_ALL_LIST_SELECTED = "is_all_list_selected"

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: ReportsViewModel
    private lateinit var adapter: ReportAdapter
    private var isAllListSelected: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViewModel()
        isAllListSelected = arguments?.getBoolean(IS_ALL_LIST_SELECTED) ?: true
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
        if (isAllListSelected) {
            viewModel.reports.observe(viewLifecycleOwner) { reports ->
                adapter.setData(reports)
            }
        } else {
            viewModel.favorites.observe(viewLifecycleOwner) { favorites ->
                adapter.setData(favorites)
            }
        }

        viewModel.lastChecked.observe(viewLifecycleOwner) { lastChecked ->
            binding.tvLastUpdate.text =
                getString(R.string.last_update_msg, lastChecked.toDateTime())
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        }
    }

    private fun initUI() {
        if (!isAllListSelected) binding.tvLastUpdate.visibility = View.GONE
        binding.swlReports.setColorSchemeColors(getPrimaryColor())
        binding.rvReports.layoutManager = LinearLayoutManager(context)
        adapter = ReportAdapter(this)
        binding.rvReports.adapter = adapter
    }

    private fun getPrimaryColor(): Int {
        val typedValue = TypedValue()
        requireContext().theme.resolveAttribute(
            androidx.appcompat.R.attr.colorPrimary,
            typedValue,
            true
        )
        return typedValue.resourceId
    }

    private fun fetchNewReports() {
        binding.swlReports.isRefreshing = true
        viewModel.isRefreshing.observe(viewLifecycleOwner) { isRefreshing ->
            if (!isRefreshing) {
                binding.swlReports.isRefreshing = false
            }
            if (isAllListSelected) {
                viewModel.loadReports()
            }
            viewModel.isRefreshing.removeObservers(viewLifecycleOwner)
        }
        viewModel.fetchReports()
    }

    private fun loadData() {
        if (isAllListSelected) {
            viewModel.loadReports()
        } else {
            viewModel.loadFavorites()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(isAllListSelected: Boolean): ListFragment {
            return ListFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_ALL_LIST_SELECTED, isAllListSelected)
                }
            }
        }
    }
}