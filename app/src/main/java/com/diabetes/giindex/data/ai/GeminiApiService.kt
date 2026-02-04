package com.diabetes.giindex.data.ai

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApiService {
    
    @POST("v1beta/models/gemini-1.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): Response<GeminiResponse>
    
    @GET("v1beta/models")
    suspend fun listModels(
        @Query("key") apiKey: String
    ): Response<ModelsListResponse>
}

data class GeminiRequest(
    val contents: List<Content>
)

data class Content(
    val parts: List<Part>
)

data class Part(
    val text: String
)

data class GeminiResponse(
    val candidates: List<Candidate>?
)

data class Candidate(
    val content: ContentResponse?
)

data class ContentResponse(
    val parts: List<PartResponse>?
)

data class PartResponse(
    val text: String?
)

data class ModelsListResponse(
    val models: List<ModelInfo>?
)

data class ModelInfo(
    val name: String?,
    val displayName: String?,
    val supportedGenerationMethods: List<String>?
)
