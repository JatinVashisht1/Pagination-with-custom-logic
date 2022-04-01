package com.example.paginationbasicspl.pagination

import android.util.Log

// key is the what kind of value we want to paginate
class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Result<List<Item>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
) : Paginator<Key, Item>{

    private var currentKey = initialKey
    // this will be true as long as we make request to our data source (database/API)
    private var isMakingRequest = false
    override suspend fun loadNextItems() {
        if(isMakingRequest){
            Log.d("defaultpaginator", "entered if statement")
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentKey)
        isMakingRequest = false
        val items = result.getOrElse { throwable->
            onError(throwable)
            onLoadUpdated(false) // we won't update if we get an error
            return
        }
        currentKey = getNextKey(items)
        onSuccess(items, currentKey)
        onLoadUpdated(false) // we have stopped loadingdata

    }

    override suspend fun reset() {
        currentKey = initialKey
    }
}