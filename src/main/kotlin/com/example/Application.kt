package com.example

import com.example.models.SpaceTrack.STSatelliteCatalog
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.repositories.Repository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

var satellitesList : List<STSatelliteCatalog>? = null

suspend fun main() {
    val a = File("efaso.json").readText(Charsets.UTF_8).toString()

    satellitesList = Json.decodeFromString<List<STSatelliteCatalog>>(a)
    satellitesList = satellitesList!!.sortedBy { it.NORADCATID!!.toInt() }

    //satellitesList = Repository().getAllSatellites()

    ///al xd = Json.encodeToString(satellitesList)
    //File("efaso.json").writeText(xd)

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
