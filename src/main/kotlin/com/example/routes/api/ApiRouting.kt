package com.example.routes.api

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.apiRouting() {
    route("api") {
        get("all") {
            call.respondText("Hello World!")
        }
    }
}