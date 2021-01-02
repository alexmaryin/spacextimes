package ru.alexmaryin.spacextimes_rx.ui.main.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.ui.main.adapter.CapsuleAdapter
import ru.alexmaryin.spacextimes_rx.ui.main.adapter.CrewAdapter
import ru.alexmaryin.spacextimes_rx.ui.main.viewmodel.SpaceXViewModel
import ru.alexmaryin.spacextimes_rx.utils.Error
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Success

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    enum class Screen { Capsules, Cores, Crew, Dragons }

    private val spaceXViewModel: SpaceXViewModel by viewModels()
    private val capsulesAdapter = CapsuleAdapter(arrayListOf())
    private val crewAdapter = CrewAdapter(arrayListOf())

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var screen: Screen = Screen.Capsules

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        swipeRefresh = findViewById(R.id.swipeView)

        setupUI()
        setupObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.capsulesSelect -> screen = Screen.Capsules
            R.id.coresSelect -> screen = Screen.Cores
            R.id.crewSelect -> screen = Screen.Crew
            R.id.dragonsSelect -> screen = Screen.Dragons
            R.id.translateSwitch -> Unit
        }
        title = item.title

        setupUI()
        setupObserver()

        return super.onOptionsItemSelected(item)
    }

    private fun renderCapsules(capsules: List<Capsule>) {
        capsulesAdapter.apply {
            addData(capsules)
            notifyDataSetChanged()
        }
    }

    private fun renderCrew(crew: List<Crew>) {
        crewAdapter.apply {
            addData(crew)
            notifyDataSetChanged()
        }
    }

    private fun setupObserver() {
        spaceXViewModel.capsules.observe(this, { result ->
            result?.let { state ->
                when (state) {
                    is Success<*> -> {
                        progressBar.visibility = View.GONE
                        (state.data as List<*>).map { it as Capsule }.apply { renderCapsules(this) }
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

        spaceXViewModel.crew.observe(this, { result ->
            result?.let { state ->
                when (state) {
                    is Success<*> -> {
                        progressBar.visibility = View.GONE
                        (state.data as List<*>).map { it as Crew }.apply { renderCrew(this) }
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
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(context, (this.layoutManager as LinearLayoutManager).orientation))
            adapter = when (screen) {
                Screen.Capsules -> capsulesAdapter
                Screen.Crew -> crewAdapter
                Screen.Cores -> capsulesAdapter
                Screen.Dragons -> capsulesAdapter
            }
        }

        swipeRefresh.setOnRefreshListener {
            when (screen) {
                Screen.Capsules -> capsulesAdapter.clear()
                Screen.Crew -> crewAdapter.clear()

                else -> Unit
            }
            setupObserver()
        }
    }
}
