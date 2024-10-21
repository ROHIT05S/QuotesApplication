package com.example.quotesapp.repository

import com.example.quotesapp.data.QuotesResult
import com.example.quotesapp.network.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class QuotesRepository @Inject constructor(private val apiService: APIService) {

    fun getQuotesRepo(): Flow<List<QuotesResult>> = flow {
        val response = apiService.getListOfQuotes()
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getQuotesRandomRepo(): Flow<List<QuotesResult>> = flow {
        val response = apiService.getRandomQuotes()
        emit(response)
    }.flowOn(Dispatchers.IO)
}