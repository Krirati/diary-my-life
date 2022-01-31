package com.kstudio.diarymylife.widgets

import android.R.attr
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.navigation.NavigationView
import com.kstudio.diarymylife.databinding.CustomBottomNavBinding
import com.kstudio.diarymylife.model.JournalCard
import com.kstudio.diarymylife.utils.Tabs
import android.R.attr.button

import android.graphics.Color

import androidx.core.graphics.drawable.DrawableCompat

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.kstudio.diarymylife.R


class CustomBottomNavigation @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr),
    NavigationView.OnNavigationItemSelectedListener {

    private var binding = CustomBottomNavBinding.inflate(LayoutInflater.from(context), this, true)
    private var _tabSelect: MutableLiveData<Int> = MutableLiveData(0)
    fun tabSelect() = _tabSelect

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

    init {
        bindingView()
    }

    private fun bindingView() = with(binding) {
        navHome.apply {
            setOnClickListener {
                _tabSelect.postValue(Tabs.HOME)
//                this.background = ContextCompat.getDrawable(context,R.drawable.ic_baseline_home_24)
//                navSetting.background = ContextCompat.getDrawable(context,R.drawable.ic_baseline_add_24)
            }
        }

        navCreate.apply {
            setOnClickListener { _tabSelect.postValue(Tabs.CREATE) }
        }

        navSetting.apply {
            setOnClickListener {
                _tabSelect.postValue(Tabs.SETTING)
//                this.background = ContextCompat.getDrawable(context,R.drawable.ic_baseline_add_24)
//                navHome.background = ContextCompat.getDrawable(context,R.drawable.ic_baseline_home_unselect_24)
            }
        }
    }

}