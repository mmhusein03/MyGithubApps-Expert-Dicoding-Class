package com.md29.husein.favorite

import android.content.Context
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
import com.md29.husein.core.domain.model.User
import com.md29.husein.core.ui.UserAdapter
import com.md29.husein.favorite.databinding.FragmentFavoriteUserBinding
import com.md29.husein.mygithubapps.R
import com.md29.husein.mygithubapps.di.FavoriteModuleDepedencies
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteUserFragment : Fragment() {
    private var _binding: FragmentFavoriteUserBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteUserViewModel: FavoriteUserViewModel by viewModels {
        factory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context,
                    FavoriteModuleDepedencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoriteUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        showRecycleView()
        getUserFavorite()
        setAction()
    }

    private fun getUserFavorite() {
        favoriteUserViewModel.getFavoriteUser.observe(viewLifecycleOwner) {
            showUser(it)
            binding.emptyFavorite.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE

        }
    }

    private fun showUser(user: List<User>) {
        val userAdapter = UserAdapter()
        userAdapter.submitList(user)
        userAdapter.onClick = {
            val toDetailFragment =
                FavoriteUserFragmentDirections.actionFavoriteUserFragmentToDetailFragment(it)
            view?.findNavController()?.navigate(toDetailFragment)
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

    private fun setAction() {
        binding.fbBack.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_favoriteUserFragment_to_homeFragment)
        }
    }

}