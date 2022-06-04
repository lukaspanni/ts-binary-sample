package de.lukaspanni.tsbinary_samples

import exampleData
import getExampleDataBinaryProtocol
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*


fun Application.configureRouting() {
    install(ContentNegotiation) {
        json()
    }


    routing {
        get("/weather-json") {
            call.respond(exampleData)
        }
        get("/weather-binary"){
            call.respondBytes(getExampleDataBinaryProtocol(), ContentType.Application.OctetStream)
        }
        get("/weather-binary-minimal"){
            call.respondBytes(getExampleDataBinaryProtocol(false), ContentType.Application.OctetStream)
        }
    }
}
