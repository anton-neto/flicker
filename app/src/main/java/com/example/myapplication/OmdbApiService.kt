    package com.example.myapplication

    import retrofit2.http.GET
    import retrofit2.http.Query

    interface OmdbApiService {
        @GET("/")
        suspend fun buscarFilme(
            @Query("t") titulo: String,
            @Query("apikey") apiKey: String = "3eb04ca2"
        ): FilmeApiResponse
    }
