package ru.alexmaryin.spacextimes_rx.ui.main.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.alexmaryin.spacextimes_rx.App
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.ui.main.adapter.CapsuleAdapter
import ru.alexmaryin.spacextimes_rx.ui.main.adapter.CrewAdapter
import ru.alexmaryin.spacextimes_rx.ui.main.viewmodel.SpaceXViewModel
import ru.alexmaryin.spacextimes_rx.utils.Error
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.Success
import ru.alexmaryin.spacextimes_rx.ui.base.BaseAdapter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    enum class Screen { Capsules, Cores, Crew, Dragons }

    private val spaceXViewModel: SpaceXViewModel by viewModels()
    private val capsulesAdapter = CapsuleAdapter()
    private val crewAdapter = CrewAdapter()

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

        setupObserver()
        changeScreen(Screen.Crew, getString(R.string.crewTitle))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.capsulesSelect -> changeScreen(Screen.Capsules, item.title.toString())
            R.id.coresSelect -> changeScreen(Screen.Cores, item.title.toString())
            R.id.crewSelect -> changeScreen(Screen.Crew, item.title.toString())
            R.id.dragonsSelect -> changeScreen(Screen.Dragons, item.title.toString())
            R.id.translateSwitch -> {
                if (item.isChecked) {
                    item.isChecked = false
                    Toast.makeText(this, getString(R.string.aiTranslateOffText), Toast.LENGTH_SHORT).show()
                    processTranslate(false)
                } else {
                    AlertDialog.Builder(this)
                        .setTitle(getString(R.string.experimentalTitle))
                        .setMessage(getString(R.string.aiTranslateAlertText))
                        .setPositiveButton(getString(R.string.agreeText)) { _: DialogInterface, _: Int -> item.isChecked = true; processTranslate(true) }
                        .setNegativeButton(getString(R.string.cancelText)) { _: DialogInterface, _: Int -> item.isChecked = false }
                        .show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeScreen(screen: Screen, itemTitle: String) {
        this.screen = screen
        title = itemTitle
        setupUI()
    }

    private fun processTranslate(switch: Boolean) {
        (application as App).settings.translateToRu = switch
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

    private inline fun <reified T: Any> itemObserver(state: Result, renderFun: (List<T>) -> Unit) =
       when (state) {
           is Success<*> -> {
               progressBar.visibility = View.GONE
               (state.data as List<*>).map { it as T }.apply { renderFun(this) }
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

    private fun setupObserver() {
        spaceXViewModel.capsules.observe(this, { result -> itemObserver(result, ::renderCapsules) })
        spaceXViewModel.crew.observe(this, { result -> itemObserver(result, ::renderCrew) })
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
            (recyclerView.adapter as BaseAdapter<*>).clear()
            setupObserver()
        }
    }
}
