package com.example.quotesapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotesapp.data.QuotesResult
import com.example.quotesapp.repository.QuotesRepository
import com.example.quotesapp.utils.CommonFunction.isNetworkAvailable
import com.example.quotesapp.utils.QuotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val repository: QuotesRepository, @ApplicationContext private val context: Context,
) : ViewModel() {
    private val _response: MutableStateFlow<QuotesState<List<QuotesResult?>>> =
        MutableStateFlow(QuotesState.Loading)
    val quoteResponse: StateFlow<QuotesState<List<QuotesResult?>>> = _response

    private val _responseRandomQuote: MutableStateFlow<QuotesState<List<QuotesResult?>>> =
        MutableStateFlow(QuotesState.Loading)
    val randomQuoteResponse: StateFlow<QuotesState<List<QuotesResult?>>> = _responseRandomQuote

    init {
        fetchQuotes()
        fetchRandomQuotes()
    }

    private fun fetchQuotes() {
        viewModelScope.launch {
            if (isNetworkAvailable(context)) {
                try {
                    val response = repository.getQuotesRepo().first()
                    _response.emit(QuotesState.Success(response))
                } catch (e: Exception) {
                    val errorMessage = "An error occurred. Please try again."
                    _response.emit(QuotesState.Error(errorMessage))
                }
            } else {
                _response.emit(QuotesState.Error("No internet connection"))
            }
        }
    }


    fun fetchRandomQuotes() {
        viewModelScope.launch {
            if (isNetworkAvailable(context)) {
                try {
                    val response = repository.getQuotesRandomRepo().first()
                    _responseRandomQuote.emit(QuotesState.Success(response))
                } catch (e: Exception) {
                    val errorMessage = "An error occurred. Please try again."
                    _responseRandomQuote.emit(QuotesState.Error(errorMessage))
                }
            } else {
                val errorMessage = "No internet connection."
                _responseRandomQuote.emit(QuotesState.Error(errorMessage))
            }
        }
    }

}


/*
//this is LiveData Code
private val _response: MutableLiveData<ResponseQuotes> = MutableLiveData()
val quoteResponse: LiveData<ResponseQuotes> = _response
init {
    fetchQuotes()
}

private fun fetchQuotes(){
    viewModelScope.launch {
        repository.getQuotesRepo()
            .catch {e->
                Log.e("MainViewModel_ERROR", "getPost: ${e.message}")
            }.collect {response->
                _response.value = response
                // postLiveData.value=response
            }

    }
}*/