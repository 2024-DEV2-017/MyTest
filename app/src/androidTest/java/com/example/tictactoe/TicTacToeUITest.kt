package com.example.tictactoe

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tictactoe.ui.theme.TicTacToeTheme
import com.example.tictactoe.viewmodels.GameViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TicTacToeUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testGamePlay() {
        val viewModel = GameViewModel()

        composeTestRule.setContent {
            TicTacToeTheme {
                TicTacToeGame(viewModel)
            }
        }

        // Simulate a game where X wins
        // Tag: cell_row_col
        composeTestRule.onNodeWithTag("cell_0_0").performClick() // X
        composeTestRule.onNodeWithTag("cell_0_0").assertTextContains("X")
        composeTestRule.onNodeWithTag("cell_0_2").performClick() // O
        composeTestRule.onNodeWithTag("cell_0_2").assertTextContains("O")

        composeTestRule.onNodeWithTag("cell_1_1").performClick() // X
        composeTestRule.onNodeWithTag("cell_1_2").performClick() // O
        composeTestRule.onNodeWithTag("cell_2_2").performClick() // X

        // Check if the game status shows that X wins
        composeTestRule.onNodeWithText("Winner: X").assertExists()
    }

    @Test
    fun testRestartGame() {
        testGamePlay()

        // Restart the game
        composeTestRule.onNodeWithText("Restart Game").performClick()

        // Check if the game status is reset
        composeTestRule.onNodeWithText("Current Player: X").assertExists()
        composeTestRule.onNodeWithText("Winner: X").assertDoesNotExist()
    }
}