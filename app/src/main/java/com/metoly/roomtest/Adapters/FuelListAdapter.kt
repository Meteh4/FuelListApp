package com.metoly.roomtest.Adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.metoly.roomtest.Model.Fuel.Fuel
import com.metoly.roomtest.R
import com.metoly.roomtest.databinding.ItemFuelBinding

class FuelListAdapter(private val onItemClick: (Fuel) -> Unit) : ListAdapter<Fuel, FuelListAdapter.FuelViewHolder>(
    FuelDiffCallback()
) {

    var onDeleteClick: (Fuel) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FuelViewHolder {
        val binding = ItemFuelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FuelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FuelViewHolder, position: Int) {
        val currentFuel = getItem(position)
        holder.bind(currentFuel)
        holder.itemView.setOnClickListener {
            onItemClick(currentFuel)
        }
        holder.itemView.findViewById<Button>(R.id.buttonDeleteFuel).setOnClickListener {
            onDeleteClick(currentFuel)
        }
    }

    inner class FuelViewHolder(private val binding: ItemFuelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fuel: Fuel) {
            binding.textViewPrice.text ="Price: ${fuel.price}"
            binding.textViewLiter.text ="Liter: ${fuel.liter}"
            binding.textViewTotalPrice.text ="Total: ${fuel.totalPrice}"
            binding.textViewType.text= "Type: ${fuel.type}"


            fuel.photo?.let { photoByteArray ->
                val bitmap = BitmapFactory.decodeByteArray(photoByteArray, 0, photoByteArray.size)
                binding.imageView.setImageBitmap(bitmap)
            }
        }
    }
}
