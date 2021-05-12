package com.mavassgn.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mavassgn.R
import java.util.*
import kotlin.collections.ArrayList

class SubCategoryAdapter(private val mContext: Context) :
    RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>() {

    private var subCategories = ArrayList<String>()
    private var filerCategories = ArrayList<String>()

    fun setListData(subCategories: ArrayList<String>) {
        this.subCategories = subCategories
        this.filerCategories = subCategories
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvListItem = itemView.findViewById<TextView>(R.id.tv_list_text)

        fun bind(data: String) {
            tvListItem.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.layout_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return filerCategories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filerCategories[position])
    }


    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filerCategories = subCategories
                } else {
                    val resultList = ArrayList<String>()
                    for (row in subCategories) {
                        if (row.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    filerCategories = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filerCategories
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filerCategories = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }

        }
    }
}