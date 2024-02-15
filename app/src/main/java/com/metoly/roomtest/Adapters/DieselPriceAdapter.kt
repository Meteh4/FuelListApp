package com.metoly.roomtest.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.metoly.roomtest.R
import com.metoly.roomtest.Model.Diesel.DieselResult

class DieselPriceAdapter(private val fuelList: List<DieselResult>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<DieselPriceAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandTextView: TextView = itemView.findViewById(R.id.brandDieselTextView)
        val katkiliTextView: TextView = itemView.findViewById(R.id.katkiliDieselTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.diesel_price, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = "viewHolder$position"

        val result = fuelList[position]

        if (result.marka != null && result.marka != "-") {
            holder.brandTextView.text = "Marka: ${result.marka}"
        }

        if (result.katkili != null && result.katkili != "-") {
            holder.katkiliTextView.text = "Katkılı Dizel: ${result.katkili}"
        } else {
            result.katkili = "0"
            holder.katkiliTextView.text = "Katkılı Dizel: Mevcut Değil"
        }

        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return fuelList.size
    }
}
