package com.md29.husein.mygithubapps.screen.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.md29.husein.core.data.Resource
import com.md29.husein.core.domain.model.Follow
import com.md29.husein.core.ui.UserFollowAdapter
import com.md29.husein.mygithubapps.R
import com.md29.husein.mygithubapps.databinding.FragmentFollowBinding
import com.md29.husein.mygithubapps.utils.ShowLoading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var showLoading: ShowLoading
    private val followViewModel: FollowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading = ShowLoading()

        setFragment()
    }

    private fun setFragment() {
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val name = arguments?.getString(USERNAME)
        when (index) {
            1 -> lifecycleScope.launch {
                name.let {
                    if (it != null) {
                        followViewModel.getFollowers(it).observe(viewLifecycleOwner) { followers ->
                            if (followers != null) {
                                when (followers) {
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
                                        setDataFollow(followers.data)
                                    }

                                    is Resource.Error -> {
                                        binding.apply {
                                            showLoading.showLoading(false, progressBar)
                                            errorLayout.root.visibility = View.VISIBLE
                                            errorLayout.tvError.text =
                                                followers.message
                                                    ?: getString(R.string.something_wrong)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            2 -> lifecycleScope.launch {
                name.let {
                    if (it != null) {
                        followViewModel.getFollowing(it).observe(viewLifecycleOwner) { following ->
                            if (following != null) {
                                when (following) {
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
                                        setDataFollow(following.data)
                                    }

                                    is Resource.Error -> {
                                        binding.apply {
                                            showLoading.showLoading(false, progressBar)
                                            errorLayout.root.visibility = View.VISIBLE
                                            errorLayout.tvError.text =
                                                following.message
                                                    ?: getString(R.string.something_wrong)
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

    }

    private fun setDataFollow(it: List<Follow>?) {
        val userAdapter = UserFollowAdapter()
        userAdapter.submitList(it)

        if (it?.isEmpty() == true) {
            binding.emptyLayout.apply {
                root.visibility = View.VISIBLE
                tvMessage.text = getString(R.string.message_follow_empty_search)
            }
        } else {
            binding.emptyLayout.root.visibility = View.GONE
        }

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = userAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val USERNAME = "username"
    }
}