package ru.alexmaryin.spacextimes_rx.ui.view.activities

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.SpaceXViewModel
import ru.alexmaryin.spacextimes_rx.utils.prepareNotificationsChannel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val spaceXViewModel: SpaceXViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).apply {
            appBarConfiguration = AppBarConfiguration(navController.graph)
            setupActionBarWithNavController(navController, appBarConfiguration)
        }

        prepareNotificationsChannel()

        if (intent.action == Intent.ACTION_SEARCH) emitSearch(intent.getStringExtra(SearchManager.QUERY))
    }

    override fun onNewIntent(intent: Intent?) {
        if (intent?.action == Intent.ACTION_SEARCH) emitSearch(intent.getStringExtra(SearchManager.QUERY))
        super.onNewIntent(intent)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()

    private fun emitSearch(query: String?) {
        Log.d("SEARCHABLE", "$query")
        if (!query.isNullOrBlank()) spaceXViewModel.searchText(query)
    }
}
