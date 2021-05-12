package com.mavassgn.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mavassgn.R
import com.mavassgn.data.model.AnimalsData
import com.squareup.picasso.Picasso


class HeaderAdapter(private val mContext:Context) : RecyclerView.Adapter<HeaderAdapter.ViewHolder>() {

    private var headers = ArrayList<AnimalsData>()

    fun setHeaderData(headers: ArrayList<AnimalsData>) {
        this.headers = headers
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val headerImage = itemView.findViewById<ImageView>(R.id.iv_header_image)

        fun bind(mContext:Context,data: AnimalsData) {
            Picasso.with(mContext).load(data.image).into(headerImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.layout_header_image,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return headers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mContext, headers[position])
    }

}