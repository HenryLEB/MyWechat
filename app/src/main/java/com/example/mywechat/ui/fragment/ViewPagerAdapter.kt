package com.example.viewpagertest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val list_fragment: List<Fragment>)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = list_fragment.size

    override fun createFragment(position: Int): Fragment {
        return list_fragment[position]
//        when (position) {
//            1 -> {
//                return MeFragment()
//            }
//            2 -> {
//                return MsgFragment()
//            }
//            3 -> {
//                return FindFragment()
//            }
//        }
//        return FriendFragment()
    }

}