package com.example.tictactoe.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

enum class Player { X, O }

data class Cell(val player: Player? = null)

class GameViewModel : ViewModel() {
    var board = mutableStateOf(List(3) { List(3) { Cell() } }) // Assume the gameboard is always 3x3
    var currentPlayer = mutableStateOf(Player.X) // Player X starts the game
    var winner = mutableStateOf<Player?>(null)
    var gameActive = mutableStateOf(true)

    var onWin: ((Player)) -> Unit? = {}
    var onDraw: () -> Unit? = {}

    fun makeMove(row: Int, col: Int) {
        if (board.value[row][col].player == null && gameActive.value) {
            board.value = board.value.mapIndexed { r, rowList ->
                rowList.mapIndexed { c, cell ->
                    if (r == row && c == col) Cell(currentPlayer.value) else cell
                }
            }
            if (checkWin()) {
                onWin(currentPlayer.value)
                winner.value = currentPlayer.value
                gameActive.value = false
            } else if (board.value.flatten().all { it.player != null }) {
                onDraw()
                gameActive.value = false
            } else { // swap active player
                currentPlayer.value = if (currentPlayer.value == Player.X) Player.O else Player.X
            }
        }
    }

    private fun checkWin(): Boolean {
        // Assuming the board is always 3x3, there are only 8 possible win conditions
        val lines = listOf(
            // Rows
            listOf(board.value[0][0], board.value[0][1], board.value[0][2]),
            listOf(board.value[1][0], board.value[1][1], board.value[1][2]),
            listOf(board.value[2][0], board.value[2][1], board.value[2][2]),
            // Columns
            listOf(board.value[0][0], board.value[1][0], board.value[2][0]),
            listOf(board.value[0][1], board.value[1][1], board.value[2][1]),
            listOf(board.value[0][2], board.value[1][2], board.value[2][2]),
            // Diagonals
            listOf(board.value[0][0], board.value[1][1], board.value[2][2]),
            listOf(board.value[0][2], board.value[1][1], board.value[2][0])
        )
        return lines.any { line ->
            line.all { it.player == currentPlayer.value }
        }
    }
}