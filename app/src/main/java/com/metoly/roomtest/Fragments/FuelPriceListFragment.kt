// FuelPriceListFragment.kt
package com.metoly.roomtest.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.metoly.roomtest.Adapters.FuelListAdapter
import com.metoly.roomtest.ViewModel.FuelViewModel
import com.metoly.roomtest.ViewModel.FuelViewModelFactory
import com.metoly.roomtest.R
import com.metoly.roomtest.databinding.FragmentFuelPriceListBinding

class FuelPriceListFragment : Fragment() {

    private var _binding: FragmentFuelPriceListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FuelViewModel by viewModels { FuelViewModelFactory(requireActivity().application) }
    private lateinit var adapter: FuelListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFuelPriceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeFuelList()

        binding.buttonAddFuel.setOnClickListener {
            findNavController().navigate(R.id.action_fuelPriceListFragment_to_addEditFuelFragment)
        }
    }

    private fun setupRecyclerView() {
        adapter = FuelListAdapter { selectedFuel ->
            val bundle = Bundle().apply {
                putInt("fuel_id", selectedFuel.id)
            }
            findNavController().navigate(R.id.action_fuelPriceListFragment_to_addEditFuelFragment, bundle)
        }

        adapter.onDeleteClick = { fuel ->
            viewModel.deleteById(fuel.id)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }


    private fun observeFuelList() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allFuel.collect { fuels ->
                adapter.submitList(fuels)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
