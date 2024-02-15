package com.metoly.roomtest.Fragments
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.metoly.roomtest.R

class PriceScreenFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private var city: String = ""
    private var district: String = ""

    companion object {
        private const val ARG_CITY = "city"
        private const val ARG_DISTRICT = "district"

        @JvmStatic
        fun newInstance(city: String, district: String) =
            PriceScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CITY, city)
                    putString(ARG_DISTRICT, district)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_price_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            city = it.getString(ARG_CITY, "")
            district = it.getString(ARG_DISTRICT, "")
        }

        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE)
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE,)

        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = TabPagerAdapter(childFragmentManager)
        adapter.addFragment(DieselPricesFragment.newInstance(city, district), "Diesel Prices")
        adapter.addFragment(GasolinePricesFragment.newInstance(city, district), "Gasoline Prices")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    class TabPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val fragmentTitleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList[position]
        }
    }
}
