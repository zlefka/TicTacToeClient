package com.example.tictactoemobile.data.mapper

import com.example.tic_tac_toe_mobile.data.dto.UserDto
import com.example.tic_tac_toe_mobile.data.entity.UserEntity
import com.example.tictactoemobile.domain.model.User

fun UserDto.toDomain(): User {
    return User(id = id, login = login)
}

fun User.toDto(): UserDto {
    return UserDto(id = id, login = login)
}

fun UserEntity.toDomain(): User {
    val domainId = serverId
    return User(id = domainId, login = login)
}

fun User.toEntity(
    password: String,
    localId: Long = 0,
    createdAt: Long = System.currentTimeMillis()
): UserEntity {
    return UserEntity(
        serverId = id,
        login = login,
        password = password,
        createdAt = createdAt
    )
}