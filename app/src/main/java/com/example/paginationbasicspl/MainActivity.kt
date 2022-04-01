package com.example.paginationbasicspl

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.paginationbasicspl.ui.theme.PaginationBasicsPlTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaginationBasicsPlTheme {
                val viewModel: MainViewModel = viewModel()
                val state = viewModel.state
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.items.size) { index ->
                            Log.d("HomeScreen", "item size is ${state.items.size} and index is $index")
                            val item = state.items[index]

                            // below if statement is a part of side effect
                            if (index >= state.items.size - 5 && !state.endReached && !state.isLoading) {
                                Log.d("If tag", "entered if statement")
                                viewModel.loadNextItems()
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text(text = item.title, fontSize = 20.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(item.description)
                            }
                        }
                        item {
                            if (state.isLoading) {
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.Center){
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
