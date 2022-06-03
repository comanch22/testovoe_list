package com.comanch.testovie_list.listFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.comanch.testovie_list.NavigationBetweenFragments
import com.comanch.testovie_list.R
import com.comanch.testovie_list.databinding.ListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val listViewModel: ListViewModel by viewModels()

    @Inject
    lateinit var navigation: NavigationBetweenFragments

    private var adapter: ListItemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
        callback.isEnabled = true
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: ListFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.list_fragment, container, false
        )

        binding.list.setHasFixedSize(true)
        binding.listViewModel = listViewModel

        adapter = ListItemAdapter(
            ItemListener { itemId ->
                listViewModel.onItemClicked(itemId)
            })

        binding.list.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner

        binding.toolbar.inflateMenu(R.menu.app_menu)
        binding.toolbar.setOnMenuItemClickListener {
            menuNavigation(it)
        }

        listViewModel.items.observe(viewLifecycleOwner) { list ->
            list?.let {
                adapter?.setData(it)
            }
            lifecycleScope.launch {
                delay(200)
                binding.list.smoothScrollToPosition(0)
            }
        }

        listViewModel.navigateToDetailFragment.observe(viewLifecycleOwner) { itemId ->
            itemId?.getContentIfNotHandled()?.let {

                navigation.navigateToDestination(
                    this,
                   ListFragmentDirections.actionListFragmentToDetailFragment(it)
                )
            }
        }

        listViewModel.itemInsert.observe(viewLifecycleOwner) { id ->
            id?.getContentIfNotHandled()?.let {
                listViewModel.getISSLocation(it)
            }
        }

        listViewModel.toast.observe(viewLifecycleOwner) { str ->
            str?.getContentIfNotHandled()?.let {

                Toast.makeText(
                    context,
                    it,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.ButtonDelete.setOnClickListener {
            listViewModel.deleteItem()
        }

        binding.ButtonPlus.setOnClickListener {
            listViewModel.insertItem()
        }

        binding.arrowBack.setOnClickListener {
            activity?.finish()
        }

        return binding.root
    }

    private fun menuNavigation(it: MenuItem) = when (it.itemId) {
        R.id.about_app -> {
            navigation.navigateToDestination(
                this,
                ListFragmentDirections.actionListFragmentToAboutAppFragment()
            )
            true
        }
        else -> false
    }
}



