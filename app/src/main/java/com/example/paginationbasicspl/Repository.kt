package com.example.paginationbasicspl

import kotlinx.coroutines.delay

class Repository {
    private val remoteDataSource  = (1..100).map{
        ListItem(
            title = "Item $it",
            description = "Description $it"
        )
    }

    // page variable is the key
    suspend fun getItems(page: Int, pageSize: Int): Result<List<ListItem>>{
        delay(2000L)
        val startingIndex = page*pageSize
        // make sure to do proper error handling in production use
        return if(startingIndex + pageSize <= remoteDataSource.size){
            Result.success(
                // slicing to simulate the next response
                remoteDataSource.slice(startingIndex until startingIndex + pageSize)
            )
        }else Result.success(emptyList())
    }
}

data class ListItem(
    val title: String = "",
    val description: String = ""
)