package com.md29.husein.mygithubapps.screen.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.md29.husein.core.data.Resource
import com.md29.husein.core.domain.model.User
import com.md29.husein.core.ui.UserAdapter
import com.md29.husein.mygithubapps.R
import com.md29.husein.mygithubapps.databinding.FragmentHomeBinding
import com.md29.husein.mygithubapps.utils.ShowLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var showLoading: ShowLoading
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading = ShowLoading()

        findUser()
        showRecycleView()
        setUpView()

    }

    private fun findUser() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.text = searchView.text
                searchView.hide()

                homeViewModel.searchUser(searchView.text.toString())
                    .observe(viewLifecycleOwner) { user ->
                        if (user != null) {
                            when (user) {
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
                                    setUserData(user.data)
                                }

                                is Resource.Error -> {
                                    binding.apply {
                                        showLoading.showLoading(false, progressBar)
                                        errorLayout.root.visibility = View.VISIBLE
                                        errorLayout.tvError.text =
                                            user.message ?: getString(R.string.something_wrong)
                                    }

                                }
                            }
                        }
                    }
                false
            }
        }
        binding.searchBar.apply {
            inflateMenu(R.menu.option_menu)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menuFavorite -> {
                        view?.findNavController()
                            ?.navigate(R.id.action_homeFragment_to_favoriteUserFragment)
                    }
                }
                true
            }
        }
    }

    private fun setUserData(items: List<User>?) {
        if (items?.isEmpty() == true) {
            binding.emptyLayout.apply {
                root.visibility = View.VISIBLE
                tvMessage.text = getString(R.string.message_empty_search)
            }
        } else {
            binding.emptyLayout.root.visibility = View.GONE
        }

        val userAdapter = UserAdapter()
        userAdapter.apply {
            submitList(items)
            onClick = {
                val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                toDetailFragment.user = it
                view?.findNavController()?.navigate(toDetailFragment)
            }
        }
        binding.rvUser.adapter = userAdapter
    }

    private fun showRecycleView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        if (context?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUser.layoutManager = GridLayoutManager(requireActivity(), 2)
        } else {
            binding.rvUser.layoutManager = layoutManager
        }
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
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
}
