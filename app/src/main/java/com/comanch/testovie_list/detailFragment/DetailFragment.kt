/*

Picasso library license

Copyright 2013 Square, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.comanch.testovie_list.detailFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.comanch.testovie_list.NavigationBetweenFragments
import com.comanch.testovie_list.R
import com.comanch.testovie_list.databinding.DetailFragmentBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by viewModels()

    @Inject
    lateinit var navigation: NavigationBetweenFragments

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val action = DetailFragmentDirections.actionDetailFragmentToListFragment()
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigation.navigateToDestination(
                this@DetailFragment,
                action
            )
        }
        callback.isEnabled = true
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: DetailFragmentBinding =
            DataBindingUtil.inflate(
                inflater, R.layout.detail_fragment, container, false
            )

        detailViewModel.setDetail(args.itemId)

        detailViewModel.toast.observe(viewLifecycleOwner) { content ->
            content.getContentIfNotHandled()?.let {
                Toast.makeText(
                    context,
                    it,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        detailViewModel.explanation.observe(viewLifecycleOwner) { content ->
            content.getContentIfNotHandled()?.let {
                binding.explanation.text = it
            }
        }

        detailViewModel.imageTitle.observe(viewLifecycleOwner) { content ->
            content.getContentIfNotHandled()?.let {
                binding.title.text = it
            }
        }

        detailViewModel.imageUrl.observe(viewLifecycleOwner) { content ->
            content.getContentIfNotHandled()?.let {
                if (it.isNotEmpty()) {
                    Picasso
                        .get()
                        .load(it)
                        .placeholder(R.drawable.ic_baseline_download_24)
                        .error(R.drawable.ic_baseline_error_24)
                        .fit()
                        .into(binding.imageNasa)
                }
            }
        }

        binding.arrowBack.setOnClickListener {
            navigation.navigateToDestination(
                this@DetailFragment,
                DetailFragmentDirections.actionDetailFragmentToListFragment()
            )
        }

        return binding.root
    }
}