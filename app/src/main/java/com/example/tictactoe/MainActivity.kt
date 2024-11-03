package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                Column {
                    Text(
                        text = "Tic Tac Toe",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 24.sp
                    )
                    Text(
                        text = "Current turn: ${viewModel.currentPlayer.value.name}",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 20.sp
                    )
                    Surface(
                        modifier = Modifier.fillMaxSize().padding(0.dp, 16.dp),
                        color = MaterialTheme.colorScheme.background
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
    val board by viewModel.board.observeAsState()
    val currentPlayer by viewModel.currentPlayer.observeAsState()
    val gameStatus by viewModel.gameStatus.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        board.forEachIndexed { rowIndex, row ->
            Row {
                row.forEachIndexed { colIndex, cell ->
                    CellView(cell = cell, onClick = { viewModel.makeMove(rowIndex, colIndex) })
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Current Player: ${currentPlayer.name}", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = gameStatus ?: "", fontSize = 20.sp, color = Color.Red)
    }
}

@Composable
fun CellView(cell: Cell, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Transparent)
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