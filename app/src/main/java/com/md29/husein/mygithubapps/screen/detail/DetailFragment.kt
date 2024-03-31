package com.md29.husein.mygithubapps.screen.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.md29.husein.core.data.Resource
import com.md29.husein.core.domain.model.User
import com.md29.husein.core.domain.model.UserDetail
import com.md29.husein.mygithubapps.R
import com.md29.husein.mygithubapps.databinding.FragmentDetailBinding
import com.md29.husein.mygithubapps.utils.SectionsPagerAdapter
import com.md29.husein.mygithubapps.utils.ShowLoading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var showLoading: ShowLoading
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var dataName: User
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading = ShowLoading()
        dataName = DetailFragmentArgs.fromBundle(arguments as Bundle).user

        setUpView()
        setUpAction()
        sectionsPage()
        getDetail()
    }

    private fun getDetail() {
        sectionsPagerAdapter.userName = dataName.name
        toggleFavorite(dataName, dataName.isFavorite)

        binding.fbShare.setOnClickListener {
            shareProfile(dataName.name)
        }

        lifecycleScope.launch {
            detailViewModel.getUserDetail(dataName.name).observe(viewLifecycleOwner) { detail ->
                if (detail != null) {
                    when (detail) {
                        is Resource.Loading -> {
                            binding.apply {
                                showLoading.showLoading(true, progressBar)
                                errorLayout.root.visibility = View.GONE
                            }
                        }

                        is Resource.Success -> {
                            binding.apply {
                                showLoading.showLoading(false, progressBar)
                                errorLayout.root.visibility = View.GONE
                            }
                            detail.data?.let { setDetailUser(it) }
                        }

                        is Resource.Error -> {
                            binding.apply {
                                showLoading.showLoading(false, progressBar)
                                errorLayout.root.visibility = View.VISIBLE
                                errorLayout.tvError.text =
                                    detail.message ?: getString(R.string.something_wrong)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setUpAction() {
        binding.fbBack.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_detailFragment_to_homeFragment)
        )
    }

    private fun toggleFavorite(item: User, isFavorite: Boolean) {
        var statusFavorite = isFavorite
        setStatusFavorite(statusFavorite)
        binding.favUser.setOnClickListener {
            statusFavorite = !statusFavorite
            detailViewModel.setFavoriteUser(item, statusFavorite)
            setStatusFavorite(statusFavorite)
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        with(binding.favUser) {
            if (statusFavorite) {
                speed = 1f
                playAnimation()
            } else {
                speed = -1f
                playAnimation()
            }
        }
    }

    private fun setDetailUser(item: UserDetail) {
        binding.apply {
            tvName.text = item.nameUser
            tvLogin.text = item.name
            tvFollow.text = item.followers.toString()
            tvFollower.text = item.following.toString()
            Glide.with(requireActivity()).load(item.avatar).into(imgItemPhoto)
        }
    }

    private fun sectionsPage() {
        sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun shareProfile(dataName: String) {
        val share = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "https://api.github.com/users/$dataName")
        }
        val chooser = Intent.createChooser(share, getString(R.string.string_share))
        startActivity(chooser)
    }

    @Suppress("DEPRECATION")
    private fun setUpView() {
        activity?.window?.decorView?.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}