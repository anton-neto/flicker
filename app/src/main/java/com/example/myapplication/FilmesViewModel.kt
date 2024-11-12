package com.example.myapplication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FilmesViewModel(private val repository: FilmesRepository) : ViewModel() {
    // State for storing movie data
    val filme = mutableStateOf<FilmeApiResponse?>(null)
    val search = mutableStateOf<SearchApiResponse?>(null)
    val error = mutableStateOf<String?>(null)

    suspend fun buscarFilmeId(id: String): FilmeApiResponse? {
        try {
            val response: FilmeApiResponse = RetrofitInstance.api.buscarFilme(id, "3eb04ca2")
            return response
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun buscarFilmeTitulo(titulo: String): SearchApiResponse? {
        try {
            val response: SearchApiResponse? = RetrofitInstance.api.buscarFilmes(titulo, "3eb04ca2")

            if (response?.Response == "True") {
                return response
            } else {
                return null
            }
        } catch (e: Exception) {
            return null
        }
    }





    suspend fun inserirFilme(filme: Filme) {
        repository.inserirFilme(filme)
    }

    suspend fun obterTodosFilmes(): List<Filme> {
        return repository.obterTodosFilmes()
    }

    suspend fun atualizarFilme(filme: Filme) {
        repository.atualizarFilme(filme)
    }

    suspend fun deletarFilme(filme: Filme) {
        repository.deletarFilme(filme)
    }

}
