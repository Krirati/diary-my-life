package com.kstudio.diarymylife.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kstudio.diarymylife.R
import com.kstudio.diarymylife.databinding.ActivityListBinding
import com.kstudio.diarymylife.domain.model.MoodViewType
import com.kstudio.diarymylife.ui.adapter.ItemCardSwipeAdapter
import com.kstudio.diarymylife.ui.base.BaseActivity
import com.kstudio.diarymylife.ui.create.CreateMoodActivity
import com.kstudio.diarymylife.ui.detail.MoodDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListActivity : BaseActivity() {

    private lateinit var binding: ActivityListBinding
    private val viewModel by viewModel<ListViewModel>()

    private val moodAdapter by lazy {
        ItemCardSwipeAdapter(
            onAddItem = { navigateToCreateJournal() },
            onDeleted = { viewModel.deleteJournal(it) },
            onNavigateToDetail = {
                navigateToActivityWithMoodId(
                    MoodDetailActivity::class.java,
                    it
                )
            },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observerLiveData()
        bindingView()
        viewModel.fetchRecentJournal()
    }

    private fun observerLiveData() {
        viewModel.getMemberList().observe(this) {
            setUpRecentMemory(it)
        }
    }

    private fun bindingView() = with(binding) {
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = moodAdapter
        }
        appBar.bindView(getString(R.string.all_moods), ::onClickBack)
    }

    private fun setUpRecentMemory(moodList: List<MoodViewType>) {
        moodAdapter.updateMoodItems(moodList)
    }

    private fun onClickBack() {
        this.onBackPressedDispatcher.onBackPressed()
    }

    private fun navigateToCreateJournal() {
        val intent = Intent(this, CreateMoodActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
    }
}