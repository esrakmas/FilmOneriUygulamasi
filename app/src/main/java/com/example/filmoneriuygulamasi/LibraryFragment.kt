package com.example.filmoneriuygulamasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.filmoneriuygulamasi.databinding.FragmentLibraryBinding
import com.google.android.material.tabs.TabLayoutMediator


/**
 *
 *Tab layout yönetimi
 *
 *
 */
class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)

        // Adapter sınıfını burada tanımlıyoruz
        val adapter = object : FragmentStateAdapter(requireActivity()) {
            override fun getItemCount(): Int = 2

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> WatchedMoviesFragment() // İzlediklerim sekmesi
                    1 -> WatchLaterFragment() // Daha Sonra İzle sekmesi
                    else -> WatchedMoviesFragment() // Varsayılan olarak ilk fragment gösterilsin
                }
            }
        }

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "İzlediklerim"
                1 -> "Daha Sonra İzle"
                else -> "İzlediklerim"
            }
        }.attach()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
