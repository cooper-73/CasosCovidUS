package com.example.casoscovidus.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.casoscovidus.R
import com.example.casoscovidus.adapters.ReportAdapter
import com.example.casoscovidus.databinding.FragmentListBinding
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
        loadData()

        return binding.root
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
    }

    private fun initUI() {
        binding.tvLastUpdate.text = getString(R.string.last_update_msg, "Now")

        binding.rvReports.layoutManager = LinearLayoutManager(context)
        adapter = ReportAdapter()
        binding.rvReports.adapter = adapter
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