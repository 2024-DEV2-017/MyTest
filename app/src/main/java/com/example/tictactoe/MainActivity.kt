package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.ui.theme.TicTacToeTheme
import com.example.tictactoe.viewmodels.Cell
import com.example.tictactoe.viewmodels.GameViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onWin = { player ->
            // Handle win: e.g. confetti anim, show dialog, etc.
        }
        viewModel.onDraw = {
            // Handle draw: e.g. change colors, show dialog, etc.
        }
        setContent {
            TicTacToeTheme {
                Column(Modifier.background(MaterialTheme.colorScheme.background)) {
                    Text(
                        text = "Tic Tac Toe",
                        modifier = Modifier.padding(0.dp, 72.dp, 0.dp, 0.dp).fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                    Surface(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TicTacToeGame(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun TicTacToeGame(viewModel: GameViewModel) {
    val board by remember { viewModel.board }
    val currentPlayer by remember { viewModel.currentPlayer }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        board.forEachIndexed { rowIndex, row ->
            Row {
                row.forEachIndexed { colIndex, cell ->
                    CellView(cell = cell, onClick = { viewModel.makeMove(rowIndex, colIndex) }, position=Pair(rowIndex, colIndex))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display winner or current player
        if (!viewModel.gameActive.value) {
            val text =  if (viewModel.winner.value != null) "Winner: ${viewModel.winner.value!!.name}"
                        else "It's a draw!"
            Text(text = text,
                fontSize = 20.sp,
                color = if (viewModel.winner.value != null) Color.Red else Color.DarkGray
            )
            Button(onClick = { viewModel.resetGame() }, Modifier.height(50.dp)) {
                Text(text = "Restart Game")
            }
        } else {
            Text(text = "Current Player: ${currentPlayer.name}", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(50.dp)) // Button placeHolder
        }
    }
}

@Composable
fun CellView(cell: Cell, position: Pair<Int, Int>?, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .testTag("cell_${position?.first}_${position?.second}")
            .background(Color.Transparent)
            .border(1.dp, MaterialTheme.colorScheme.onBackground, MaterialTheme.shapes.small)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = cell.player?.name ?: "",
            fontSize = 36.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}