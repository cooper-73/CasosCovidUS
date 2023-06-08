package com.example.casoscovidus.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.casoscovidus.R
import com.example.casoscovidus.data.models.Report
import com.example.casoscovidus.databinding.ReportItemBinding
import com.example.casoscovidus.utils.FragmentType
import com.example.casoscovidus.utils.formatWithSeparator
import com.example.casoscovidus.utils.toDate
import com.example.casoscovidus.viewmodels.ReportsViewModel

class ReportAdapter(owner: ViewModelStoreOwner, val fragmentHolderType: FragmentType) :
    RecyclerView.Adapter<ReportAdapter.ViewHolder>() {

    private var reports: MutableList<Report> = mutableListOf()
    private var viewModel: ReportsViewModel

    init {
        viewModel = ViewModelProvider(owner)[ReportsViewModel::class.java]
    }

    inner class ViewHolder(private val binding: ReportItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(report: Report, position: Int) {
            val context = binding.root.context

            // Binds data of report to item UI
            binding.tvDate.text = context.getString(R.string.date_msg, report.date.toDate())
            binding.tvPositive.text = context.getString(
                R.string.positive_msg,
                report.positive.formatWithSeparator(),
                report.positiveIncrease.formatWithSeparator()
            )
            binding.tvDeath.text = context.getString(
                R.string.death_msg,
                report.death.formatWithSeparator(),
                report.deathIncrease.formatWithSeparator()
            )
            binding.chbFavorite.isChecked = report.isFavorite

            // Sets listeners for views of item UI
            binding.chbFavorite.setOnCheckedChangeListener { buttonView, isFavorite ->
                if (buttonView.isPressed) {
                    when (fragmentHolderType) {
                        FragmentType.ALL -> {
                            changeFavoriteValueAt(position, isFavorite)
                        }

                        FragmentType.FAVORITES -> {
                            if (!isFavorite) removeItem(position)
                        }
                    }
                    viewModel.setFavoriteFieldOfReport(report.id, isFavorite)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ReportItemBinding.inflate(LayoutInflater.from(parent.context))

        // Sets view root with width MATCH_PARENT and height WRAP_CONTENT
        binding.root.apply {
            this.layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = reports.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val report = reports[position]
        holder.bind(report, position)
    }

    // Sets the data of the items
    fun setData(newReports: List<Report>) {
        reports = newReports.toMutableList()
        notifyItemRangeChanged(0, newReports.size)
    }

    // Removes item by position and notifies changes
    fun removeItem(position: Int) {
        reports = reports.filterIndexed { index, _ -> index != position }.toMutableList()
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
        viewModel.setDataEmptyStatus(reports.isEmpty())
    }

    // Sets the favorite field of item at passed position
    private fun changeFavoriteValueAt(position: Int, isFavorite: Boolean) {
        reports = reports.mapIndexed { index, report ->
            if (index == position) report.copy(isFavorite = isFavorite)
            else report
        }.toMutableList()
    }
}