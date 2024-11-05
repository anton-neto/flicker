package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FilmesViewModel(private val repository: FilmesRepository) : ViewModel() {

    // Função para inserir um filme no repositório
    fun inserirFilme(filme: Filme) {
        viewModelScope.launch {
            repository.inserirFilme(filme)
        }
    }

    // Função para buscar um filme na API OMDb
    fun buscarFilme(titulo: String, onSuccess: (FilmeApiResponse) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val filme = RetrofitInstance.api.buscarFilme(titulo)
                if (filme.Response == "True") {
                    onSuccess(filme)
                } else {
                    onError("Filme não encontrado")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Erro ao buscar filme")
            }
        }
    }
}
