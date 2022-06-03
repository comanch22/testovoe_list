package com.comanch.testovie_list.aboutFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.comanch.testovie_list.NavigationBetweenFragments
import com.comanch.testovie_list.R
import com.comanch.testovie_list.databinding.AboutAppFragmentBinding
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AboutAppFragment : Fragment() {

    private val aboutAppViewModel: AboutAppViewModel by viewModels()

    @Inject
    lateinit var navigation: NavigationBetweenFragments

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {

            navigation.navigateToDestination(
               this@AboutAppFragment, AboutAppFragmentDirections.actionAboutAppFragmentToListFragment()
           )
        }
        callback.isEnabled = true

        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: AboutAppFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.about_app_fragment, container, false
        )

        binding.aboutAppViewModel = aboutAppViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        aboutAppViewModel.ossLicense.observe(viewLifecycleOwner) {

            startActivity(Intent(context, OssLicensesMenuActivity::class.java))
        }

        binding.arrowBackAboutApp.setOnClickListener {

            navigation.navigateToDestination(
                this, AboutAppFragmentDirections.actionAboutAppFragmentToListFragment()
            )
        }
        return binding.root
    }
}