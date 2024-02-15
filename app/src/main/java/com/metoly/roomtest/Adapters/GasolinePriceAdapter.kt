package com.metoly.roomtest.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.metoly.roomtest.R
import com.metoly.roomtest.Model.Gasoline.GasolineResult

class GasolinePriceAdapter(private val fuelList: List<GasolineResult>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<GasolinePriceAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandTextView: TextView = itemView.findViewById(R.id.brandGasolineTextView)
        val benzinTextView: TextView = itemView.findViewById(R.id.gasolineTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gasoline_price, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = "viewHolder$position"

        val result = fuelList[position]

        if (result.marka != null && result.marka != "-") {
            holder.brandTextView.text = "Marka: ${result.marka}"
        }

        if (result.benzin != null && result.benzin != "-") {
            holder.benzinTextView.text = "Katkılı Dizel: ${result.benzin}"
        } else {
            result.benzin = "0"
            holder.benzinTextView.text = "Katkılı Dizel: Mevcut Değil"
        }

        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return fuelList.size
    }
}
