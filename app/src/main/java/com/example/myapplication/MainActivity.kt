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
                FilmesScreen(modifier = Modifier.padding(innerPadding), viewModel = filmesViewModel)
            }
        }
    }
}

@Composable
fun FilmesScreen(modifier: Modifier = Modifier, viewModel: FilmesViewModel) {
    val estadoFilmes = remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = estadoFilmes.value,
            onValueChange = { estadoFilmes.value = it },
            label = { Text("Digite o título do filme") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.buscarFilme(estadoFilmes.value,
                onSuccess = { response ->
                    // Aqui você pode lidar com a resposta, por exemplo, armazenar no banco ou exibir
                    println("Filme encontrado: ${response.Title}")
                },
                onError = { errorMessage ->
                    // Aqui você pode lidar com o erro
                    println("Erro: $errorMessage")
                }
            )
        }) {
            Text("Buscar Filme")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFilmesScreen() {
    // Usar um ViewModel simulado ou vazio
    val filmesRepository = FilmesRepository(AppDatabase.getDatabase(ApplicationProvider.getApplicationContext()).filmesDao())
    val filmesViewModel = FilmesViewModel(filmesRepository)

    FilmesScreen(viewModel = filmesViewModel)
}
