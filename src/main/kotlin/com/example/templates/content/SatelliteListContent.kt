package com.example.templates.content

import com.example.orm.models.SatelliteDAO
import com.example.orm.models.Satellites
import io.ktor.server.html.*
import kotlinx.html.*
import org.jetbrains.exposed.sql.transactions.transaction

class SatelliteListContent: Template<FlowContent> {
    var page : Int? = null
    lateinit var sats : List<SatelliteDAO>
    override fun FlowContent.apply() {
        val itemsXPage : Long = 30;
        if(page == null)
            page = 1

        transaction {
            sats = SatelliteDAO.all().limit(itemsXPage.toInt(), itemsXPage*page!!-itemsXPage).toList()
        }

        for (sat in sats) {
            div("card") {
                div("container") {
                    h4 {
                        b { +"${sat.objectName}" }
                    }
                    p { +"${sat.noradCatId}" }
                    a {
                        href = "/view_satellite?id=" + sat.noradCatId
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