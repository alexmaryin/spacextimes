package ru.alexmaryin.spacextimes_rx.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.ui.main.adapter.CapsuleAdapter
import ru.alexmaryin.spacextimes_rx.ui.main.viewmodel.CapsulesViewModel
import ru.alexmaryin.spacextimes_rx.utils.Error
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Success

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val capsulesViewModel: CapsulesViewModel by viewModels()
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

        setupObserver()
        setupUI()
    }

    private fun renderList(capsules: List<Capsule>) {
        capsulesAdapter.apply {
            addData(capsules)
            notifyDataSetChanged()
        }
    }

    private fun setupObserver() {
        capsulesViewModel.capsules.observe(this, { result ->
            result?.let { state ->
                when (state) {
                    is Success<*> -> {
                        progressBar.visibility = View.GONE
                        (state.data as List<*>).map { it as Capsule }.apply { renderList(this) }
                        recyclerView.visibility = View.VISIBLE
                        swipeRefresh.isRefreshing = false
                    }
                    is Error -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, state.msg, Toast.LENGTH_LONG).show()
                    }
                    is Loading -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun setupUI() {
        capsulesAdapter = CapsuleAdapter(arrayListOf())
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(context, (this.layoutManager as LinearLayoutManager).orientation))
            adapter = capsulesAdapter
        }

        swipeRefresh.setOnRefreshListener {
            capsulesAdapter.clear()
            setupObserver()
        }
    }
}