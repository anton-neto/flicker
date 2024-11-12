package com.example.myapplication

import android.media.Image
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
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
     lateinit var filmesViewModel: FilmesViewModel

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
    val busca = remember { mutableStateOf("") }
    val anoLancamento = remember { mutableStateOf("") }
    val status = remember { mutableStateOf("não assistido") }
    val filmesList = remember { mutableStateListOf<FilmeApiResponse>() }
    val filmeId = remember { mutableStateOf("") } // New field for specifying movie ID to update/delete

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Input for the movie ID
        TextField(
            value = busca.value,
            onValueChange = { busca.value = it },
            label = { Text("ID do Filme") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Button to add a new movie
        Button(onClick = {
            scope.launch {
                if(viewModel.buscarFilmeId(busca.value) != null) {
                    val FilmeBusca: FilmeApiResponse? = viewModel.buscarFilmeId(busca.value)
                    filmesList.clear()
                    if (FilmeBusca != null) {
                        if (FilmeBusca.Year != null) {
                            val novoFilme = FilmeApiResponse(
                                Title = FilmeBusca.Title,
                                Year = FilmeBusca.Year,
                                imdbID = FilmeBusca.imdbID,
                                Type = FilmeBusca.Type,
                                Poster = FilmeBusca.Poster,
                            )
                            filmesList.clear()
                            filmesList.add(novoFilme)
                        }else{
                            filmesList.clear()
                            val FilmeBusca2: SearchApiResponse? = viewModel.buscarFilmeTitulo(busca.value)

                            if (FilmeBusca2 != null) {
                                filmesList.addAll(FilmeBusca2.Search.map {
                                    FilmeApiResponse(
                                        Title = it.Title,
                                        Year = it.Year,
                                        imdbID = it.imdbID,
                                        Type = it.Type,
                                        Poster = it.Poster
                                    )
                                })
                            } else {
                                //
                            }

                        }
                    }

                    if(FilmeBusca == null){
                        filmesList.clear()
                    }
                }

            }
        }){
            Text("Listar Filmes")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))


        if(filmesList.size == 0){
            Text("Termo de busca incorreto!")
        }else{
            filmesList.forEach { filme ->
                Text("ID: ${filme.imdbID}, Título: ${filme.Title}, Ano: ${filme.Year}")
                Text(text = "${filmesList.size}")
            }
        }

    }
}
