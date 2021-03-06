package com.udacity.asteroidradar.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.AsteroidsDataFilter
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val asteroidAdapter: AsteroidListAdapter by lazy {
        AsteroidListAdapter(AsteroidClickListener { asteroid ->
            view?.findNavController()?.navigate(MainFragmentDirections.actionShowDetail(asteroid))
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            asteroidRecycler.adapter = asteroidAdapter
            viewModel = mainViewModel
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mainViewModel.updateFilter(
            when (item.itemId) {
                R.id.show_saved_menu -> AsteroidsDataFilter.SAVED_ASTEROIDS
                R.id.show_today_menu -> AsteroidsDataFilter.TODAY_ASTEROIDS
                else -> AsteroidsDataFilter.WEEK_ASTEROIDS
            }
        )
        return true
    }
}
