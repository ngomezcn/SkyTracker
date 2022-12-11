package com.example.templates

import com.example.routes.web.WebRoutesEnum
import com.example.templates.content.*
import io.ktor.server.html.*
import kotlinx.html.*
import java.io.IOException


class LayoutTemplate: Template<HTML> {

    val header = Placeholder<FlowContent>()
    var route : String = ""

    // Pages content
    val satelliteListContent = TemplatePlaceholder<SatelliteListContent>()
    val viewSatelliteContent = TemplatePlaceholder<ViewSatelliteContent>()
    val homeContent = TemplatePlaceholder<HomeContent>()

    // Default structure
    private val navigationContent = TemplatePlaceholder<NavigationContent>()
    private val footerContent = TemplatePlaceholder<FooterContent>()

    override fun HTML.apply() {

        if(route == "") {
            throw IOException("No se ha indicado la ruta")
        }

        head {
            meta {
                charset = "utf-8"
            }
            meta {
                name = "viewport"
                content = "width=device-width, initial-scale=1, shrink-to-fit=no"
            }
            meta {
                name = "description"
                content = ""
            }
            meta {
                name = "author"
                content = ""
            }
            title {
            }
            link {
                rel = "icon"
                type = "image/x-icon"
                href = "assets/favicon.ico"
            }
            link {
                href = "https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css"
                rel = "stylesheet"
            }
            link {
                href = "css/styles.css"
                rel = "stylesheet"
            }
        }

        body {
            insert(NavigationContent(), navigationContent)
            when(route) {

                WebRoutesEnum.home.route -> insert(HomeContent(), homeContent)
                WebRoutesEnum.satellites.route -> insert(SatelliteListContent(), satelliteListContent)
                WebRoutesEnum.view_satellite.route -> insert(ViewSatelliteContent(), viewSatelliteContent)
            }
            insert(FooterContent(), footerContent)
        }
    }
}



