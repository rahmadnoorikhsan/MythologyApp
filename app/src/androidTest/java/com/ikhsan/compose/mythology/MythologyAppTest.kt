package com.ikhsan.compose.mythology

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.ikhsan.compose.mythology.model.MythologiesData
import com.ikhsan.compose.mythology.ui.navigation.Screen
import com.ikhsan.compose.mythology.ui.theme.MythologyTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MythologyAppTest{

    @get: Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            MythologyTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                MythologyApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        composeTestRule.onNodeWithText("Search").assertIsDisplayed()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigateToDetailWithData() {
        composeTestRule.run {
            onNodeWithTag("list_item").performScrollToIndex(11)
            onNodeWithText(MythologiesData.dummyMythologies[11].title).performClick()
            navController.assertCurrentRouteName(Screen.DetailMythology.route)
            onNodeWithText(MythologiesData.dummyMythologies[11].title).assertIsDisplayed()
        }
    }

    @Test
    fun navHost_onFavoriteClick_shouldExistInFavoriteScreen() {
        composeTestRule.run {
            onNodeWithTag("list_item").performScrollToIndex(0)
            onAllNodesWithTag("fav_button")[0].performClick()
            onNodeWithText("Favorite").performClick()
            navController.assertCurrentRouteName(Screen.Favorite.route)
            onNodeWithText(MythologiesData.dummyMythologies[0].title).assertIsDisplayed()
        }
    }

    @Test
    fun navHost_onFavoriteClick_removeFromFavoriteScreen() {
        composeTestRule.run {
            onNodeWithTag("list_item").performScrollToIndex(0)
            onAllNodesWithTag("fav_button")[0].performClick()
            onNodeWithText("Favorite").performClick()
            navController.assertCurrentRouteName(Screen.Favorite.route)
            onNodeWithText(MythologiesData.dummyMythologies[0].title).assertIsDisplayed()
            onNodeWithTag("fav_button").performClick()
            onNodeWithTag("empty_content").assertIsDisplayed()
        }
    }

    @Test
    fun search_validQuery_showResult() {
        val validQuery = "Pega"
        composeTestRule.run {
            onNodeWithText("Search").performTextInput(validQuery)
            onNodeWithTag("search_icon").performClick()
            onNodeWithText("Pegasus").assertIsDisplayed()
        }
    }

    @Test
    fun search_invalidQuery_showEmptyContent() {
        val invalidQuery = "$%*^9"
        composeTestRule.run {
            onNodeWithText("Search").performTextInput(invalidQuery)
            onNodeWithTag("search_icon").performClick()
            onNodeWithTag("empty_content").assertIsDisplayed()
        }
    }

    @Test
    fun navHost_profileScreen() {
        composeTestRule.run {
            onNodeWithText("Profile").performClick()
            onNodeWithText("Rahmad Noor Ikhsan").assertIsDisplayed()
        }
    }
}