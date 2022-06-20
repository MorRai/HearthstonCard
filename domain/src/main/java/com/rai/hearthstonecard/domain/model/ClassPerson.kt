package com.rai.hearthstonecard.domain.model

import kotlinx.serialization.Serializable


@Suppress("PLUGIN_IS_NOT_ENABLED")
@Serializable
data class ClassPerson(
    val id: Int,
    val name: String,
    var image: String?,
    var slug: String?,
    val cardId: Int
):java.io.Serializable