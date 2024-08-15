package com.spyker3d.easyjob.search.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.spyker3d.easyjob.R
import com.spyker3d.easyjob.utils.exactDpToPxHeightBased
import com.spyker3d.easyjob.utils.getCorrectionCoefficient

class VacancyPositionSuggestsAdapter(
    private val context: Context,
    private val hostTextView: AutoCompleteTextView
) : BaseAdapter(), Filterable {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var suggestionsList: MutableList<String> = mutableListOf()
    private val itemPaddingDp = context.resources.getDimension(R.dimen.suggestions_drop_down_vertical_padding)
    private var maxListDisplaySize = context.resources.getInteger(R.integer.suggestions_drop_down_max_list_items_count)
    private val correctionCoefficient = getCorrectionCoefficient(context)

    fun add(item: String) {
        suggestionsList.add(item)
        adjustDropDownSize()
        this.notifyDataSetChanged()
    }

    private fun adjustDropDownSize() {
        val suggestionListSizeToCalc =
            if (suggestionsList.size > maxListDisplaySize) maxListDisplaySize else suggestionsList.size
        hostTextView.dropDownHeight =
            (hostTextView.lineHeight - correctionCoefficient.toInt() + exactDpToPxHeightBased(
                context,
                itemPaddingDp.toInt()
            )) * suggestionListSizeToCalc
    }

    fun applyDataSet(items: Collection<String>) {
        suggestionsList.clear()
        suggestionsList.addAll(items)
        this.notifyDataSetChanged()
    }

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
        adjustDropDownSize()
    }

    override fun getCount(): Int {
        return suggestionsList.count()
    }

    override fun getItem(position: Int): Any {
        return suggestionsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (convertView != null) {
            (convertView as TextView).text = suggestionsList[position]
            return convertView
        }
        val newView = inflater.inflate(R.layout.suggestion_view_holder, null)
        (newView as TextView).text = suggestionsList[position]
        return newView
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    filterResults.values = suggestionsList.toList()
                    filterResults.count = suggestionsList.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    suggestionsList.clear()
                    suggestionsList.addAll(results.values as Collection<String>)
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}
