package com.example.myapplication

class ListasFilmesRepository(private val listasFilmesDao: ListasFilmesDao) {
    suspend fun inserirLista(lista: ListaFilmes) = listasFilmesDao.inserirLista(lista)
    suspend fun atualizarLista(lista: ListaFilmes) = listasFilmesDao.atualizarLista(lista)
    suspend fun deletarLista(lista: ListaFilmes) = listasFilmesDao.deletarLista(lista)
    suspend fun obterTodasListas() = listasFilmesDao.obterTodasListas()
}
