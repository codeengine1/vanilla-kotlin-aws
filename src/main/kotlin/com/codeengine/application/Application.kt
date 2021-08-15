package com.codeengine.application

import com.codeengine.admin.routing.admin
import com.codeengine.admin.session.GoogleOauthUserSession
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.sessions.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.conf.global
import org.kodein.di.instance
import org.kodein.di.ktor.di
import org.slf4j.Logger

fun Application.main() {
    val logger = this.environment.log
    val config = this.environment.config
    val jsonHttpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
    }
    DI.global.addConfig {
        bind<ApplicationConfig>() with instance(config)
        bind<Logger>() with instance(logger)
        bind<HttpClient>(tag = "json") with instance(jsonHttpClient)
    }
    di { extend(DI.global) }
    install(Sessions) {
        cookie<GoogleOauthUserSession>("user_session")
    }
    install(Authentication) {
        oauth("auth-oauth-google") {
            urlProvider = { "http://localhost:9090/admin/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = System.getenv("GOOGLE_CLIENT_ID"),
                    clientSecret = System.getenv("GOOGLE_CLIENT_SECRET"),
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile")
                )
            }
            client = jsonHttpClient
        }
    }
    routing {
        admin()
    }
}
