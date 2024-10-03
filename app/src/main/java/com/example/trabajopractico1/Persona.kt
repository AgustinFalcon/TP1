package com.example.trabajopractico1

import java.io.Serializable


data class Persona(
    val userName: String,
    val password: String,
    val color: Colors?
): Serializable

