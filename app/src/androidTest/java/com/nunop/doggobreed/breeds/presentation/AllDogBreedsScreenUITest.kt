package com.nunop.doggobreed.breeds.presentation

import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.nunop.doggobreed.MainActivity
import com.nunop.doggobreed.breedDetails.presentation.BreedDetailsScreen
import com.nunop.doggobreed.navigation.Route
import com.nunop.doggobreed.ui.theme.DoggoBreedTheme
import com.nunop.doggobreed.utils.MockServerDispatcherAndroid
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@HiltAndroidTest
class AllDogBreedsScreenUITest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

//    @get:Rule
//    val activityScenarioRule = ActivityScenarioRule<MainActivity>()

    @get:Rule(order = 2)
//    val composeRule = createAndroidComposeRule<MainActivity>()
    val composeRule = createComposeRule()

    private lateinit var server: MockWebServer
    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start(9090)

        hiltRule.inject()

        composeRule.setContent {
//        composeRule.activity.setContent {
            DoggoBreedTheme {
                navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.DOG_BREEDS,
                        modifier = Modifier.padding(it)
                    ) {
                        composable(Route.DOG_BREEDS) {
                            AllDogBreedsScreen(onNextClick = { breed, subBreed ->
                                if (subBreed != null) {
                                    navController.navigate(Route.BREED_DETAILS + "/$breed" + "/$subBreed")
                                } else {
                                    navController.navigate(Route.BREED_DETAILS + "/$breed")
                                }
                            })
                        }

                        composable(
                            route = Route.BREED_DETAILS + "/{breed}/{subBreed}",
                            arguments = listOf(
                                navArgument("breed") {
                                    type = NavType.StringType
                                },
                                navArgument("subBreed") {
                                    type = NavType.StringType
                                    nullable = true
                                    defaultValue = null
                                }
                            )
                        ) { nav ->
                            val breed = nav.arguments?.getString("breed")
                            val subBreed = nav.arguments?.getString("subBreed", null)
                            breed?.let { it1 -> BreedDetailsScreen(it1, subBreed) }
                        }

                        composable(
                            route = Route.BREED_DETAILS + "/{breed}",
                            arguments = listOf(
                                navArgument("breed") {
                                    type = NavType.StringType
                                }
                            )
                        ) { nav ->
                            val breed = nav.arguments?.getString("breed")
                            breed?.let { it1 -> BreedDetailsScreen(it1, null) }
                        }
                    }
                }
            }
        }


    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    //TODO: wait for a new release which solve the bug: https://issuetracker.google.com/issues/235703118
    @Test
    fun getBreeds_success() {
        server.dispatcher = MockServerDispatcherAndroid().RequestDispatcherSuccess()

        composeRule
//            .onAllNodesWithContentDescription("Breed").assertCountEquals(96)
            .onAllNodesWithContentDescription("Breed").assertCountEquals(7)


        assertThat(
            navController.currentDestination?.route?.startsWith(Route.DOG_BREEDS)
        )
    }

//    @Test
//    fun getBreeds_error() {
//
//    }
}