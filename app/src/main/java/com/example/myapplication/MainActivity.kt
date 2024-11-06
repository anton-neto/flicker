package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private lateinit var filmesViewModel: FilmesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o ViewModel
        val filmesRepository = FilmesRepository(AppDatabase.getDatabase(applicationContext).filmesDao())
        filmesViewModel = ViewModelProvider(this, FilmesViewModelFactory(filmesRepository)).get(FilmesViewModel::class.java)

        setContent {
            CRUDScreen(filmesViewModel)
        }
    }
}

@Composable
fun CRUDScreen(viewModel: FilmesViewModel) {
    val scope = rememberCoroutineScope()
    val titulo = remember { mutableStateOf("") }
    val anoLancamento = remember { mutableStateOf("") }
    val status = remember { mutableStateOf("não assistido") }
    val filmesList = remember { mutableStateListOf<Filme>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campos de texto para inserir os dados do filme
        TextField(
            value = titulo.value,
            onValueChange = { titulo.value = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = anoLancamento.value,
            onValueChange = { anoLancamento.value = it },
            label = { Text("Ano de Lançamento") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Botão para adicionar filme
        Button(onClick = {
            scope.launch {
                val novoFilme = Filme(titulo = titulo.value, anoLancamento = anoLancamento.value, status = status.value)
                viewModel.inserirFilme(novoFilme)
                atualizarListaFilmes(viewModel, filmesList)
            }
        }) {
            Text("Adicionar Filme")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão para listar todos os filmes
        Button(onClick = {
            scope.launch {
                atualizarListaFilmes(viewModel, filmesList)
            }
        }) {
            Text("Listar Filmes")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Exibe a lista de filmes
        filmesList.forEach { filme ->
            Text("ID: ${filme.id}, Título: ${filme.titulo}, Ano: ${filme.anoLancamento}, Status: ${filme.status}")
        }
    }
}

// Função auxiliar para atualizar a lista de filmes
suspend fun atualizarListaFilmes(viewModel: FilmesViewModel, filmesList: MutableList<Filme>) {
    filmesList.clear()
    filmesList.addAll(viewModel.obterTodosFilmes())
}
