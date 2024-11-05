package com.example.myapplication
import androidx.room.*

@Dao
interface ListasFilmesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirLista(lista: ListaFilmes)

    @Update
    suspend fun atualizarLista(lista: ListaFilmes)

    @Delete
    suspend fun deletarLista(lista: ListaFilmes)

    @Query("SELECT * FROM listas_filmes WHERE id = :id")
    suspend fun obterListaPorId(id: Int): ListaFilmes?

    @Query("SELECT * FROM listas_filmes")
    suspend fun obterTodasListas(): List<ListaFilmes>
}
