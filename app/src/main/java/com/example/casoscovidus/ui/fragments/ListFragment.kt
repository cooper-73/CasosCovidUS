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

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: ReportsViewModel
    private lateinit var adapter: ReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        bindViewModel()
        initObservers()
        initUI()
        loadData()

        return binding.root
    }

    private fun bindViewModel() {
        viewModel = ViewModelProvider(this)[ReportsViewModel::class.java]
    }

    private fun initObservers() {
        viewModel.reports.observe(viewLifecycleOwner) { reports ->
            adapter.setData(reports)
        }
    }

    private fun initUI() {
        binding.tvLastUpdate.text = getString(R.string.last_update_msg, "Now")

        binding.rvReports.layoutManager = LinearLayoutManager(context)
        adapter = ReportAdapter()
        binding.rvReports.adapter = adapter
    }

    private fun loadData() {
        viewModel.loadReports()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}