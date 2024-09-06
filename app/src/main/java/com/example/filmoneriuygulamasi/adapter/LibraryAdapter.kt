package com.example.filmoneriuygulamasi.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.filmoneriuygulamasi.WatchLaterFragment
import com.example.filmoneriuygulamasi.WatchedMoviesFragment

class LibraryAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2 // Two tabs: Watched, Watch Later
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> WatchedMoviesFragment() // Tab 1: İzlediklerim
            1 -> WatchLaterFragment() // Tab 2: Daha Sonra İzle
            else -> Fragment() // Default case
        }
    }
}

