package com.example.paginationbasicspl.pagination

// using generics
interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    suspend fun reset()
}