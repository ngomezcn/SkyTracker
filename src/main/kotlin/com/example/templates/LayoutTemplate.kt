package com.example.templates

import com.example.models.SpaceTrack.STSatelliteCatalog
import com.example.routes.web.WebRoutesEnum
import com.example.satellitesList
import io.ktor.server.html.*
import kotlinx.html.*

class LayoutTemplate: Template<HTML> {

    val header = Placeholder<FlowContent>()
    val satelliteListContent = TemplatePlaceholder<SatelliteListContent>()
    val viewSatelliteContent = TemplatePlaceholder<ViewSatelliteContent>()
    var route : String = ""

    override fun HTML.apply() {
        body {

            h1 {
                insert(header)
            }

            when(route) {
                WebRoutesEnum.satellites.route -> insert(SatelliteListContent(), satelliteListContent)
                WebRoutesEnum.view_satellite.route -> insert(ViewSatelliteContent(), viewSatelliteContent)
            }
        }
    }
}

class SatelliteListContent: Template<FlowContent> {
    var page : Int? = null

    override fun FlowContent.apply() {
        val itemsXPage = 10;
        if(page == null)
            page = 1

        for (i in itemsXPage* page!! -itemsXPage until itemsXPage* page!!) {
            div("card") {
                div("container") {
                    h4 {
                        b { +"${satellitesList?.get(i)!!.OBJECTNAME}" }
                    }
                    p { +"${satellitesList?.get(i)!!.NORADCATID}" }
                    a {
                        href = "/view_satellite?id=" + satellitesList?.get(i)!!.NORADCATID.toString()
                        +"""View more"""
                    }
                }
            }
        }


        a {
            href = "/satellites?page=" + (page!!-1)
            +"""<-----------|  """
        }

        a {
            href = "/satellites?page=" + (page!!+1)
            +"""  |---------->"""
        }
    }
}

class ViewSatelliteContent: Template<FlowContent> {
    lateinit var sat : STSatelliteCatalog

    override fun FlowContent.apply() {
        p{
            +sat.OBJECTNAME!!
        }
        p{
            +sat.NORADCATID!!
        }
    }
}

