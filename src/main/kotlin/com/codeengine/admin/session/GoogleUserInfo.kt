package com.codeengine.admin.session

import com.fasterxml.jackson.annotation.JsonProperty

data class GoogleUserInfo(
    val id: String,
    val name: String,
    @JsonProperty("given_name") val givenName: String,
    @JsonProperty("family_name") val familyName: String,
    val picture: String,
    val locale: String
)
