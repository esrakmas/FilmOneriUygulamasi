package com.example.filmoneriuygulamasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class LibraryFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_library, container, false)

        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)

        // Adapter set up
        viewPager.adapter = LibraryAdapter(this)

        // Attach TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "İzlediklerim"
                1 -> "Daha Sonra İzle"
                else -> null
            }
        }.attach()

        return view
    }

    private inner class LibraryAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return 2 // Two tabs: Watched, Watch Later
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> WatchedFragment() // Tab 1: İzlediklerim
                1 -> WatchLaterFragment() // Tab 2: Daha Sonra İzle
                else -> Fragment() // Default case
            }
        }
    }
}
