package com.example.quotesapp.network

import com.example.quotesapp.data.QuotesResult
import com.example.quotesapp.utils.NetworkConstants.QUOTES_LIST
import com.example.quotesapp.utils.NetworkConstants.TODAY_QUOTE
import retrofit2.http.GET

interface APIService {

   /* @GET(QUOTES_LIST)
    suspend fun getListOfQuotes(): ResponseQuotes*/

    @GET(QUOTES_LIST)
    suspend fun getListOfQuotes(): List<QuotesResult>

    @GET(TODAY_QUOTE)
    suspend fun getRandomQuotes(): List<QuotesResult>
}