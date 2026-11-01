package org.example.project.peresentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.project.network.model.CurrencyDtoItem
import org.example.project.network.remote.ApiRepository

class MainViewModel(private val repository: ApiRepository) : ViewModel()  {

    private val _currency = mutableStateListOf<CurrencyDtoItem>()

    val currency: List<CurrencyDtoItem> = _currency
    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init{

        load()

    }
    fun load(){
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            val result = repository.getCurrency()
            if (result.isSuccess){
                _currency.clear()
                _currency.addAll(result.getOrThrow())
                isLoading = false
            }
            else if (result.isFailure){
                errorMessage = result.exceptionOrNull()?.message ?: "sorry something went wrong"
                isLoading = false

            }
        }
    }
    fun refresh(){
        load()
    }

    fun load(query: String){
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            val result = repository.getCurrency(query)
            if (result.isSuccess){
                _currency.clear()
                _currency.addAll(result.getOrThrow())
                isLoading = false
            }
            else if (result.isFailure){
                errorMessage = result.exceptionOrNull()?.message ?: "sorry something went wrong"
                isLoading = false

            }
        }
    }





}