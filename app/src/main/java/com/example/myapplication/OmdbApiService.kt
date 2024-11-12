    package com.example.myapplication

    import retrofit2.http.GET
    import retrofit2.http.Query

    interface OmdbApiService {
        @GET("/")
        suspend fun buscarFilme(
            @Query("i") id: String,
            @Query("apikey") apiKey: String = "3eb04ca2"
        ): FilmeApiResponse

        suspend fun buscarFilmes(
            @Query("s") titulo: String,
           @Query("apikey") apiKey: String = "3eb04ca2"
        ): SearchApiResponse
    }
