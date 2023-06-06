package com.example.casoscovidus.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.casoscovidus.R
import com.example.casoscovidus.adapters.ReportAdapter
import com.example.casoscovidus.data.models.Report
import com.example.casoscovidus.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: ReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        initUI()

        return binding.root
    }

    private fun initUI() {
        binding.tvLastUpdate.text = getString(R.string.last_update_msg, "Now")

        binding.rvReports.layoutManager = LinearLayoutManager(context)
        adapter = ReportAdapter(
            listOf(
                Report(1L, 2L, 3L, 4L, 5L, true),
                Report(2L, 3L, 4L, 5L, 6L, false),
                Report(1L, 2L, 3L, 4L, 5L, true),
                Report(2L, 3L, 4L, 5L, 6L, false),
                Report(1L, 2L, 3L, 4L, 5L, true),
                Report(2L, 3L, 4L, 5L, 6L, false),
                Report(1L, 2L, 3L, 4L, 5L, true),
                Report(2L, 3L, 4L, 5L, 6L, false),
                Report(1L, 2L, 3L, 4L, 5L, true),
                Report(2L, 3L, 4L, 5L, 6L, false),
                Report(1L, 2L, 3L, 4L, 5L, true),
                Report(2L, 3L, 4L, 5L, 6L, false),
            ))
        binding.rvReports.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}