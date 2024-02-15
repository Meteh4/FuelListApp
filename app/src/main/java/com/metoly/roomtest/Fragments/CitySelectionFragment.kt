package com.metoly.roomtest.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.metoly.roomtest.R

class CitySelectionFragment : Fragment() {
    private lateinit var citySpinner: Spinner
    private lateinit var districtSpinner: Spinner
    private lateinit var confirmationListener: OnConfirmationListener

    interface OnConfirmationListener {
        fun onConfirmation(city: String, district: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnConfirmationListener) {
            confirmationListener = context
        } else {
            throw RuntimeException("$context must implement OnConfirmationListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city_selection, container, false)

        citySpinner = view.findViewById(R.id.citySpinner)
        districtSpinner = view.findViewById(R.id.districtSpinner)

        val cityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cityList)
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySpinner.adapter = cityAdapter

        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCity = citySpinner.selectedItem.toString()
                val districtList = districtMap[selectedCity]?.toMutableList() ?: mutableListOf()
                val districtAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, districtList)
                districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                districtSpinner.adapter = districtAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val confirmButton: Button = view.findViewById(R.id.confirmButton)
        confirmButton.setOnClickListener {
            val selectedCity = citySpinner.selectedItem.toString()
            val selectedDistrict = districtSpinner.selectedItem.toString()

            val englishCity = turkishToEnglish(selectedCity)
            val englishDistrict = turkishToEnglish(selectedDistrict)

            println(englishCity)
            println(englishDistrict)

            confirmationListener.onConfirmation(englishCity, englishDistrict)
        }

        return view
    }

    companion object {
        private val cityList = listOf(
            "Adana", "Ankara", "Antalya", "Bursa", "Diyarbakır",
            "Eskişehir", "Gaziantep", "Istanbul", "Izmir", "Konya"
        )

        private val districtMap = mapOf(
            "Adana" to listOf("Aladağ", "Ceyhan", "Çukurova", "Feke", "Karaisalı"),
            "Ankara" to listOf("Altındağ", "Çankaya", "Etimesgut", "Gölbaşı", "Yenimahalle"),
            "Antalya" to listOf("Aksu", "Döşemealtı", "Kepez", "Konyaaltı", "Muratpaşa"),
            "Bursa" to listOf("Gemlik", "Gürsu", "Nilüfer", "Osmangazi", "Yıldırım"),
            "Diyarbakır" to listOf("Bağlar", "Kayapınar", "Sur", "Yenişehir", "Başkale"),
            "Eskişehir" to listOf("Odunpazarı", "Tepebaşı"),
            "Gaziantep" to listOf("Araba", "Şahinbey", "Şehitkamil"),
            "Istanbul" to listOf("Adalar", "Bakırköy", "Beşiktaş", "Kadıköy", "Üsküdar"),
            "Izmir" to listOf("Bayraklı", "Bornova", "Karabağlar", "Konak", "Menderes"),
            "Konya" to listOf("Beyşehir", "Ereğli", "Karapınar", "Selçuklu", "Meram")
        )

        fun turkishToEnglish(input: String): String {
            return input
                .toLowerCase()
                .replace("ı", "i")
                .replace("ğ", "g")
                .replace("I", "i")
                .replace("ü", "u")
                .replace("ş", "s")
                .replace("ç", "c")
                .replace("ö", "o")

        }
    }
}
