package com.kelompokpam.kidsfirst.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kelompokpam.kidsfirst.R
import com.kelompokpam.kidsfirst.presentation.artikel.ListArtikelFragment

class TabsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        // Mengembalikan Fragment yang sesuai dengan posisi Tab yang aktif
        return when (position) {
            0 -> ListArtikelFragment()
            1 -> ListArtikelFragment()
            2 -> ListArtikelFragment()
            else -> throw IllegalStateException("Invalid tab position")
        }
    }

    override fun getCount(): Int {
        // Mengembalikan jumlah total Tab
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // Mengembalikan judul Tab berdasarkan posisi
        return when (position) {
            0 -> context.getString(R.string.untuk_anda)
            1 -> context.getString(R.string.following)
            2 -> context.getString(R.string.kehamilan)
            else -> null
        }
    }
}