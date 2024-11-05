package com.example.myapplication

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "filmes",
    foreignKeys = [ForeignKey(
        entity = ListaFilmes::class,
        parentColumns = ["id"],
        childColumns = ["listaId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["listaId"])] // Adiciona índice à coluna listaId
)
data class Filme(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val anoLancamento: String,
    val status: String, // "não assistido" ou "assistido"
    val listaId: Int? = null // ID da lista associada
)
