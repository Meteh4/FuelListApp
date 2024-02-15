package com.metoly.roomtest.Adapters

import androidx.recyclerview.widget.DiffUtil
import com.metoly.roomtest.Model.Fuel.Fuel

class FuelDiffCallback : DiffUtil.ItemCallback<Fuel>() {
    override fun areItemsTheSame(oldItem: Fuel, newItem: Fuel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Fuel, newItem: Fuel): Boolean {
        return oldItem == newItem
    }
}
