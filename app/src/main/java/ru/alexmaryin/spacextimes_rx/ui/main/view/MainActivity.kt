package ru.alexmaryin.spacextimes_rx.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.api.Api
import ru.alexmaryin.spacextimes_rx.data.api.ApiServiceImpl
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.ui.base.ViewModelFactory
import ru.alexmaryin.spacextimes_rx.ui.main.adapter.CapsuleAdapter
import ru.alexmaryin.spacextimes_rx.ui.main.viewmodel.CapsulesViewModel
import ru.alexmaryin.spacextimes_rx.utils.Status

class MainActivity : AppCompatActivity() {

    private lateinit var capsulesViewModel: CapsulesViewModel
    private lateinit var capsulesAdapter: CapsuleAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        swipeRefresh = findViewById(R.id.swipeView)

        setupUI()
        setupViewModel()
        setupObserver()

        swipeRefresh.setOnRefreshListener {
            capsulesAdapter.clear()
            setupObserver()
        }
    }

    private fun renderList(capsules: List<Capsule>) {
        capsulesAdapter.addData(capsules)
        capsulesAdapter.notifyDataSetChanged()
    }

    private fun setupObserver() {
        capsulesViewModel.getCapsules().observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { capsules -> renderList(capsules) }
                    recyclerView.visibility = View.VISIBLE
                    swipeRefresh.isRefreshing = false
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }
        })
    }

    private fun setupViewModel() {
        capsulesViewModel = ViewModelProvider(this, ViewModelFactory(Api(ApiServiceImpl()))).get(CapsulesViewModel::class.java)
    }

    private fun setupUI() {
        capsulesAdapter = CapsuleAdapter(arrayListOf())
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(context, (this.layoutManager as LinearLayoutManager).orientation))
            adapter = capsulesAdapter
        }
    }
}