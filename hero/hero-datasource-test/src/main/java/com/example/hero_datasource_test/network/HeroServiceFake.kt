package com.example.hero_datasource_test.network

import com.example.hero_datasource.netwok.HeroService
import com.example.hero_datasource.netwok.HeroServiceImpl
import com.example.hero_datasource_test.network.data.HeroDataEmpty
import com.example.hero_datasource_test.network.data.HeroDataMalformed
import com.example.hero_datasource_test.network.data.HeroDataValid
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*

class HeroServiceFake {

    private val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
    private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"


    fun build(
        type: HeroServiceResponseType
    ): HeroService{
         val client =  HttpClient(MockEngine){
             install(ContentNegotiation){
              json(
                    contentType = ContentType.Application.Json,
                    json = kotlinx.serialization.json.Json {
                        ignoreUnknownKeys = true
                    }
                )
             }
             engine {
                 addHandler{ request ->
                     when (request.url.fullUrl) {
                         "https://api.opendota.com/api/heroStats" -> {
                             val responseHeaders = headersOf(
                                 "Content-Type" to listOf("application/json", "charset=utf-8")
                             )
                             when(type){
                                 is HeroServiceResponseType.EmptyList -> {
                                     respond(
                                         HeroDataEmpty.data,
                                         status = HttpStatusCode.OK,
                                         headers = responseHeaders
                                     )
                                 }
                                 is HeroServiceResponseType.MalformedData -> {
                                     respond(
                                         HeroDataMalformed.data,
                                         status = HttpStatusCode.OK,
                                         headers = responseHeaders
                                     )
                                 }
                                 is HeroServiceResponseType.GoodData -> {
                                     respond(
                                         HeroDataValid.data,
                                         status = HttpStatusCode.OK,
                                         headers = responseHeaders
                                     )
                                 }
                                 is HeroServiceResponseType.Http404 -> {
                                     respond(
                                         HeroDataEmpty.data,
                                         status = HttpStatusCode.NotFound,
                                         headers = responseHeaders
                                     )
                                 }
                             }
                         }
                         else -> error("Unhandled ${request.url.fullUrl}")
                     }
                 }
             }
        }

        return HeroServiceImpl(client)
    }
}