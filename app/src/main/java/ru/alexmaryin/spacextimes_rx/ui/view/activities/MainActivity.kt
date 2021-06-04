package ru.alexmaryin.spacextimes_rx.ui.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.utils.prepareNotificationsChannel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).apply {
            appBarConfiguration = AppBarConfiguration(navController.graph)
            setupActionBarWithNavController(navController, appBarConfiguration)
        }

        prepareNotificationsChannel()
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
}
