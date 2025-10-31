    package com.santiago.pantallastrabajodegrado.data

    import retrofit2.http.GET

    interface ApiService {
        // --- CORRECCIÓN AQUÍ: Endpoint actualizado al nombre de tu archivo ---
        @GET("gistfile1.txt")
        suspend fun getPreguntas(): List<ApiPregunta>
    }