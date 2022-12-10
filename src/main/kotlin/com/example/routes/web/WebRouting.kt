package com.example.routes.web

import com.example.models.SpaceTrack.STSatelliteCatalog
import com.example.satellitesList
import com.example.templates.LayoutTemplate
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

enum class WebRoutesEnum(val route: String) {
    home("/"),
    satellites("satellites"),
    view_satellite("view_satellite"),
}

fun Route.webRouting() {
    route("/") {
        get("/") {
            call.respondHtmlTemplate(LayoutTemplate()) {
                route = WebRoutesEnum.home.route

                header {
                    +"Home"
                }
            }
        }

        get(WebRoutesEnum.satellites.toString()) {
            var requestedPage : Int? = null

            if(!call.request.queryParameters["page"].isNullOrBlank()) {
                requestedPage = call.request.queryParameters["page"]!!.toInt()
                if(requestedPage.toInt() > 50)
                    call.respondRedirect("/satellites?page=50")
                if(requestedPage.toInt() < 1)
                    call.respondRedirect("/satellites")
            }

            call.respondHtmlTemplate(LayoutTemplate()) {
                route = WebRoutesEnum.satellites.route

                header {
                    +"List"
                }

                this.satelliteListContent {
                    page = requestedPage
                }
            }
        }
        get(WebRoutesEnum.view_satellite.toString()) {
            var id : String = ""

            if(call.request.queryParameters["id"].isNullOrBlank()) {
                call.respondRedirect("/satellites")
            } else {
                id = call.request.queryParameters["id"]!!
                if(satellitesList!!.find { it.NORADCATID!! == id } == null) {
                    call.respondRedirect("/satellites")
                } else
                {
                    val selSat : STSatelliteCatalog = satellitesList!!.find { it.NORADCATID!! == id }!!
                    call.respondHtmlTemplate(LayoutTemplate()) {
                        route = WebRoutesEnum.view_satellite.route

                        header {
                            +selSat.OBJECTNAME!!
                        }

                        this.viewSatelliteContent {
                            sat = selSat
                        }
                    }
                }
            }
        }
    }
}