package com.example.tic_tac_toe_mobile.data.api

import com.example.tic_tac_toe_mobile.data.dto.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GameApi {
    @POST("/game/signup")
    suspend fun registerUser(@Body request: SignUpRequestDto): SignUpResponseDto

    @POST("/game/login")
    suspend fun loginUser(@Body request: LoginRequestDto): LoginResponseDto

    @POST("/game/new")
    suspend fun newGame(): GameDto

    @GET("/game/{id}")
    suspend fun getGameById(@Path("id") id: String): GameDto

    @GET("/game/available")
    suspend fun availableGames(): List<GameDto>

    @GET("/game/user/{id}")
    suspend fun getUserById(@Path("id") id: String): UserDto

    @POST("/game/{id}/join")
    suspend fun joinGame(@Path("id") id: String): GameDto

    @POST("/game/{id}/move")
    suspend fun makeMove(@Path("id") id: String, @Body move: MoveDto): GameDto
}