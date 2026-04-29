package com.example.tictactoemobile.data.api

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {
    var login: String? = null
    var password: String? = null
}