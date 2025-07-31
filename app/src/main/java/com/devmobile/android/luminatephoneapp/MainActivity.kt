package com.devmobile.android.luminatephoneapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devmobile.android.luminatephoneapp.MainActivity.Route.Calls
import com.devmobile.android.luminatephoneapp.MainActivity.Route.Contact
import com.devmobile.android.luminatephoneapp.MainActivity.Route.Contacts
import com.devmobile.android.luminatephoneapp.MainActivity.Route.Keypad
import com.devmobile.android.luminatephoneapp.ui.screens.CallsScreen
import com.devmobile.android.luminatephoneapp.ui.screens.ContactScreen
import com.devmobile.android.luminatephoneapp.ui.screens.ContactsScreen
import com.devmobile.android.luminatephoneapp.ui.screens.KeypadScreen
import com.devmobile.android.luminatephoneapp.ui.theme.LuminatePhoneAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LuminatePhoneAppTheme {
                Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                    NavigationBar()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NavigationBar(modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val bottomNavItems = listOf(
            NavItem(
                Calls,
                stringResource(R.string.calls),
                R.drawable.ic_recent,
                stringResource(R.string.calls)
            ), NavItem(
                Keypad,
                stringResource(R.string.keypad),
                R.drawable.ic_keypad,
                stringResource(R.string.keypad)
            ), NavItem(
                Contacts,
                stringResource(R.string.contacts),
                R.drawable.ic_contacts,
                stringResource(R.string.contacts)
            )
        )

        Scaffold(
            modifier = modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar(
                    modifier = Modifier,
                    containerColor = Color.White.copy(alpha = 0.4f),
                    tonalElevation = 16.dp,
                ) {

                    bottomNavItems.forEach { navItem ->
                        val selected = currentRoute == navItem.route.route

                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(navItem.route.route)
                            },
                            colors = NavigationBarItemDefaults.colors().copy(
                                selectedIndicatorColor = Color.Transparent,
                            ),
                            icon = {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(navItem.icon),
                                    contentDescription = null,
                                    tint = if (selected) Color.Black else Color.DarkGray
                                )
                            }, label = {
                                Text(
                                    text = navItem.label,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        )
                    }
                }
            }
        ) { contentPadding ->

            AppNavHost(
                navController = navController,
                startRoute = Keypad.route,
                modifier = Modifier
                    .consumeWindowInsets(contentPadding)
                    .padding(contentPadding)
            )
        }
    }

    @SuppressLint("ViewModelConstructorInComposable")
    @Composable
    private fun AppNavHost(
        navController: NavHostController, startRoute: String, modifier: Modifier = Modifier
    ) {
        NavHost(
            navController = navController, startDestination = startRoute, modifier = modifier
        ) {
            composable(route = Calls.route) {
                CallsScreen(viewModel = hiltViewModel())
            }
            composable(route = Keypad.route) {

                KeypadScreen(viewModel = hiltViewModel())
            }
            composable(route = Contacts.route) { backStack ->

                ContactsScreen(viewModel = hiltViewModel()) { contactId ->
                    navController.navigate(route = Contacts.routeToNavigate(contactId))
                }
            }
            composable(
                route = Contact.route,
                arguments = listOf(navArgument("contactId") { type = NavType.IntType }),
            ) { backStack ->

                val contactId = backStack.arguments!!.getInt("contactId")

                ContactScreen(
                    contactId = contactId,
                    viewModel = hiltViewModel(),
                    onNavigationUp = { navController.navigateUp() },
                    onShareContact = { contactData ->
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TITLE, contactData.name)
                            putExtra(
                                Intent.EXTRA_TEXT, """
                                ${contactData.number}
                                ${contactData.email}
                            """.trimIndent()
                            )
                        }
                        startActivity(
                            Intent.createChooser(
                                intent, "Share via"
                            )
                        )
                    })
            }
        }
    }

    data class NavItem(
        val route: Route, val label: String, val icon: Int, val contentDescription: String
    )

    sealed class Route(val route: String) {

        object Calls : Route(route = "calls")
        object Keypad : Route(route = "keypad")
        object Contacts : Route(route = "contacts") {
            fun routeToNavigate(id: Int): String {
                return "contacts/$id"
            }
        }

        data class Contact(val contactId: Int) : Route(route = "contacts/$contactId") {
            companion object {
                const val route = "contacts/{contactId}"
            }
        }

        data class NewContact(val number: String = "") : Route(route = "new_contact")
        data class Settings(val number: String = "") : Route(route = "settings")

    }
}