package com.md29.husein.mygithubapps.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.md29.husein.mygithubapps.screen.follow.FollowFragment

class SectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    var userName: String = ""

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.ARG_SECTION_NUMBER, position + 1)
            putString(FollowFragment.USERNAME, userName)
        }
        return fragment
    }

    override fun getItemCount(): Int = 2
}