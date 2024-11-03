package com.example.tictactoe

import com.example.tictactoe.viewmodels.GameViewModel
import com.example.tictactoe.viewmodels.Player
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GameViewModelTest {

    private lateinit var viewModel: GameViewModel

    @Before
    fun setUp() {
        viewModel = GameViewModel()
    }

    @Test
    fun testInitialState() = runTest {
        assertEquals(Player.X, viewModel.currentPlayer.value)
        assertNull(viewModel.winner.value)
        assertTrue(viewModel.gameActive.value)
        assertTrue(viewModel.board.value.flatten().all { it.player == null })
    }

    @Test
    fun testMakeFirstMoves() = runTest {
        viewModel.makeMove(0, 0)
        assertEquals(Player.X, viewModel.board.value[0][0].player)
        assertEquals(Player.O, viewModel.currentPlayer.value)
        viewModel.makeMove(1, 0)
        assertEquals(Player.O, viewModel.board.value[1][0].player)
        assertEquals(Player.X, viewModel.currentPlayer.value)
    }

    @Test
    fun testWinCondition() = runTest {
        viewModel.makeMove(0, 0) // X
        viewModel.makeMove(1, 0) // O
        viewModel.makeMove(0, 1) // X
        viewModel.makeMove(1, 1) // O
        viewModel.makeMove(0, 2) // X wins

        assertEquals(viewModel.winner.value, Player.X)
    }

    @Test
    fun testDrawCondition() = runTest {
        viewModel.makeMove(0, 0) // X
        viewModel.makeMove(0, 1) // O
        viewModel.makeMove(0, 2) // X
        viewModel.makeMove(1, 1) // O
        viewModel.makeMove(1, 0) // X
        viewModel.makeMove(1, 2) // O
        viewModel.makeMove(2, 1) // X
        viewModel.makeMove(2, 0) // O
        viewModel.makeMove(2, 2) // X

        assertNull(viewModel.winner.value)
        assertFalse(viewModel.gameActive.value)
    }

    @Test
    fun testRestartGame() = runTest {
        viewModel.makeMove(0, 0)
        viewModel.resetGame()

        testInitialState()
    }
}