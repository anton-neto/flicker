package com.example.myapplication

class FilmesRepository(private val filmesDao: FilmesDao) {
    suspend fun inserirFilme(filme: Filme) = filmesDao.inserirFilme(filme)
    suspend fun atualizarFilme(filme: Filme) = filmesDao.atualizarFilme(filme)
    suspend fun deletarFilme(filme: Filme) = filmesDao.deletarFilme(filme)
    suspend fun obterTodosFilmes() = filmesDao.obterTodosFilmes()
}
