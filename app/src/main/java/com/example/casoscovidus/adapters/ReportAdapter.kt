package com.example.casoscovidus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.casoscovidus.R
import com.example.casoscovidus.data.models.Report
import com.example.casoscovidus.databinding.ReportItemBinding

class ReportAdapter() :
    RecyclerView.Adapter<ReportAdapter.ViewHolder>() {

    private var reports: List<Report> = listOf()

    inner class ViewHolder(private val binding: ReportItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(report: Report) {
            val context = binding.root.context
            binding.tvDate.text = context.getString(R.string.date_msg, report.date.toString())
            binding.tvPositive.text =
                context.getString(R.string.positive_msg, report.positive, report.positiveIncrease)
            binding.tvDeath.text =
                context.getString(R.string.death_msg, report.death, report.deathIncrease)
            binding.chbFavorite.isChecked = report.isFavorite

            binding.chbFavorite.setOnCheckedChangeListener { _, isChecked ->
                val msg = if (isChecked) "Marked as favorite" else "Unmarked as favorite"
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ReportItemBinding.inflate(LayoutInflater.from(parent.context))

        // Sets view root with width MATCH_PARENT and height WRAP_CONTENT
        binding.root.apply {
            this.layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = reports.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val report = reports[position]
        holder.bind(report)
    }

    fun setData(reports: List<Report>?) {
        this.reports = reports ?: listOf()
        notifyItemRangeChanged(0, reports?.size ?: 0)
    }
}