package com.codeengine.admin.routing

import com.codeengine.admin.session.GoogleOauthUserSession
import com.codeengine.admin.session.GoogleUserInfo
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import kotlinx.html.*
import kotlinx.serialization.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.client.features.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.delay
import org.gradle.internal.impldep.org.eclipse.jgit.lib.ObjectChecker.tag
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import org.kodein.di.ktor.di
import org.slf4j.MDC
import java.math.BigDecimal
import java.net.URLEncoder
import java.util.*
import kotlin.Exception
import kotlin.ExperimentalStdlibApi
import kotlin.Long
import kotlin.String
import kotlin.Throwable
import kotlin.getValue

fun Route.admin() {
    val httpClient by closestDI().instance<HttpClient>(tag = "json")

    authenticate("auth-oauth-google") {
        get("/admin/login") {
            // Redirects to 'authorizeUrl' automatically
        }

        get("/admin/callback") {
            val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
            call.sessions.set(GoogleOauthUserSession(principal?.accessToken.toString()))
            call.respondRedirect("/admin/hello")
        }
    }
    get("/admin/") {
        call.respondHtml {
            body {
                p {
                    a("/admin/login") { +"Login with Google" }
                }
            }
        }
    }
    get("/admin/hello") {
        val userSession: GoogleOauthUserSession? = call.sessions.get<GoogleOauthUserSession>()

        if (userSession == null) {
            call.respondRedirect("/admin/")
            return@get
        }

        val userInfo: GoogleUserInfo = httpClient.get("https://www.googleapis.com/oauth2/v2/userinfo", ) {
            headers {
                append(HttpHeaders.Authorization, "Bearer ${userSession.token}")
            }
        }
        call.respondText("Hello, ${userInfo.name}!")
    }
}
