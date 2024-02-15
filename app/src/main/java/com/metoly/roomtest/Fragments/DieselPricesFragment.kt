package com.metoly.roomtest.Fragments

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.metoly.roomtest.Model.Diesel.Diesel
import com.metoly.roomtest.Adapters.DieselPriceAdapter
import com.metoly.roomtest.Api.DieselPriceService
import com.metoly.roomtest.R
import com.metoly.roomtest.Model.Diesel.DieselResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DieselPricesFragment : Fragment(), DieselPriceAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var loadingTextView: TextView
    private lateinit var sortButton: Button
    private var fuelList = mutableListOf<DieselResult>()
    private var isAscendingOrder = true
    private var city: String = ""
    private var district: String = ""
    private lateinit var adapter: DieselPriceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diesel_prices, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.itemAnimator = null
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = DieselPriceAdapter(fuelList, this)
        recyclerView.adapter = adapter

        progressBar = view.findViewById(R.id.progressBar)
        loadingTextView = view.findViewById(R.id.loadingTextView)
        sortButton = view.findViewById(R.id.sortButton)

        sortButton.setOnClickListener {
            sortData()
        }

        arguments?.let {
            city = it.getString(ARG_SELECTED_CITY, "")
            district = it.getString(ARG_SELECTED_DISTRICT, "")
        }

        getDieselPrice()

        return view
    }

    private fun getDieselPrice() {
        progressBar.visibility = View.VISIBLE
        loadingTextView.visibility = View.VISIBLE

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val dieselPriceService = retrofit.create(DieselPriceService::class.java)

        val contentType = "application/json"
        val authorization = "apikey 4WdEyeDk0hZZKyJQmrzhxB:6op9dYILR1g9MdzCmxZybe"

        val call = dieselPriceService.getDieselPrice(district, city, contentType, authorization)

        call.enqueue(object : Callback<Diesel> {
            override fun onResponse(call: Call<Diesel>, response: Response<Diesel>) {
                progressBar.visibility = View.GONE
                loadingTextView.visibility = View.GONE

                if (response.isSuccessful) {
                    val fuelData = response.body()
                    fuelData?.result?.let {
                        fuelList.clear()
                        fuelList.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    println("Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Diesel>, t: Throwable) {
                progressBar.visibility = View.GONE
                loadingTextView.visibility = View.GONE

                println("Request failed: ${t.message}")
            }
        })
    }

    private fun sortData() {
        fuelList.sortWith(compareBy { it.katkili?.toDoubleOrNull() ?: Double.MAX_VALUE })

        if (!isAscendingOrder) {
            fuelList.reverse()
            sortButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0)
        } else {
            sortButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
        }
        animateSort()

        updateZeroValues()

        adapter.notifyItemRangeChanged(0, fuelList.size)

        isAscendingOrder = !isAscendingOrder
    }

    private fun animateSort() {
        val totalItemCount = fuelList.size
        val parentView = recyclerView.parent as ViewGroup
        val recyclerViewWidth = recyclerView.width.toFloat()
        val itemWidth = recyclerViewWidth / totalItemCount

        val animatorSet = AnimatorSet()
        val animators = mutableListOf<Animator>()

        for ((index, item) in fuelList.withIndex()) {
            val viewHolder = parentView.findViewWithTag<View>("viewHolder$index")
            viewHolder?.let { itemView ->
                val originalX = -recyclerViewWidth
                val targetPosition = index * itemWidth
                itemView.translationX = originalX
                val animator = ObjectAnimator.ofFloat(itemView, "translationX", originalX, 0f)
                animator.apply {
                    duration = 500
                    startDelay = (index * 50).toLong()
                }
                animators.add(animator)
            }
        }

        for (animator in animators) {
            animatorSet.play(animator)
        }

        animatorSet.start()
    }

    private fun updateZeroValues() {
        for (item in fuelList) {
            if (item.katkili == "0") {
                item.katkili = null
            }
            if (item.motorin == "0") {
                item.motorin = null
            }
        }
    }

    override fun onItemClick(position: Int) {
        val selectedDiesel = fuelList[position]
        val katkiliValue = selectedDiesel.katkili?.toDoubleOrNull() ?: 0.0
        val bundle = Bundle().apply {
            putDouble("price", katkiliValue)
            putString("type", "Diesel")
        }
        findNavController().navigate(R.id.action_priceScreenFragment_to_addEditFuelFragment, bundle)
    }

    companion object {
        private const val BASE_URL = "https://api.collectapi.com/"
        private const val ARG_SELECTED_CITY = "selectedCity"
        private const val ARG_SELECTED_DISTRICT = "selectedDistrict"

        @JvmStatic
        fun newInstance(selectedCity: String, selectedDistrict: String) =
            DieselPricesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SELECTED_CITY, selectedCity)
                    putString(ARG_SELECTED_DISTRICT, selectedDistrict)
                }
            }
    }
}
