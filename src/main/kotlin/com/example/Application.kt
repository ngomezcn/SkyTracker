package com.example

import com.example.models.SpaceTrack.STSatelliteCatalog
import com.example.orm.ORM
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File


suspend fun main() {




    ORM.connect()
    ORM.createSchemas()
   ORM.loadInitialData()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
