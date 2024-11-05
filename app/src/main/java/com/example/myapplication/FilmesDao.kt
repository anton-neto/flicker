package com.example.myapplication
import androidx.room.*


@Dao
interface FilmesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirFilme(filme: Filme)

    @Update
    suspend fun atualizarFilme(filme: Filme)

    @Delete
    suspend fun deletarFilme(filme: Filme)

    @Query("SELECT * FROM filmes WHERE id = :id")
    suspend fun obterFilmePorId(id: Int): Filme?

    @Query("SELECT * FROM filmes")
    suspend fun obterTodosFilmes(): List<Filme>
}
