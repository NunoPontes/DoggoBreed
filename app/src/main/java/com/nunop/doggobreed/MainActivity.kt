package com.nunop.doggobreed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nunop.doggobreed.breedDetails.presentation.BreedDetailsScreen
import com.nunop.doggobreed.breeds.presentation.AllDogBreedsScreen
import com.nunop.doggobreed.navigation.Route
import com.nunop.doggobreed.ui.theme.DoggoBreedTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoggoBreedTheme {
                val navController = rememberNavController()
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
}
