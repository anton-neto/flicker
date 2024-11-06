package com.example.myapplication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FilmesViewModel(private val repository: FilmesRepository) : ViewModel() {
    // State for storing movie data
    val filme = mutableStateOf<FilmeApiResponse?>(null)
    val error = mutableStateOf<String?>(null)

    fun buscarFilme(titulo: String, onSuccess: (FilmeApiResponse) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.buscarFilme(titulo, "3eb04ca2")
                filme.value = response
                onSuccess(response)
            } catch (e: Exception) {
                error.value = e.message
                onError(e.message ?: "Erro desconhecido")
            }
        }
    }
    suspend fun inserirFilme(filme: Filme) {
        repository.inserirFilme(filme)
    }

    suspend fun obterTodosFilmes(): List<Filme> {
        return repository.obterTodosFilmes()
    }

}
