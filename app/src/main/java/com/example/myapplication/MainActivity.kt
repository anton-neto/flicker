package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ApplicationProvider

class MainActivity : ComponentActivity() {
    private lateinit var filmesViewModel: FilmesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializa o ViewModel
        val filmesRepository = FilmesRepository(AppDatabase.getDatabase(applicationContext).filmesDao())
        filmesViewModel = ViewModelProvider(this, FilmesViewModelFactory(filmesRepository)).get(FilmesViewModel::class.java)

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Uso correto do estado com mutableStateOf
                    val estadoFilmes = remember { mutableStateOf("") }

                    // Campo de texto para pesquisar o filme
                    TextField(
                        value = estadoFilmes.value,  // Acesso ao valor
                        onValueChange = { estadoFilmes.value = it },  // Atualiza o valor
                        label = { Text("Digite o título do filme") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botão para buscar o filme
                    Button(onClick = {
                        filmesViewModel.buscarFilme(estadoFilmes.value,
                            onSuccess = { response ->
                                // Aqui os dados do filme são retornados com sucesso
                                println("Filme encontrado: ${response.Title}")
                            },
                            onError = { errorMessage ->
                                // Lida com o erro, se ocorrer
                                println("Erro: $errorMessage")
                            }
                        )
                    }) {
                        Text("Buscar Filme")
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Exibe os dados do filme ou mensagem de erro
                    filmesViewModel.filme.value?.let {
                        // Exibe as informações do filme
                        Text("Título: ${it.Title}", style = MaterialTheme.typography.titleMedium)
                        Text("Ano: ${it.Year}", style = MaterialTheme.typography.bodyMedium)
                        Text("Gênero: ${it.Genre}", style = MaterialTheme.typography.bodyMedium)
                        Text("IMDb: ${it.imdbRating}", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Sinopse: ${it.Plot}", style = MaterialTheme.typography.bodyMedium)
                    }

                    // Se houver um erro, exibe a mensagem de erro
                    filmesViewModel.error.value?.let {
                        Text("Erro: $it", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}
