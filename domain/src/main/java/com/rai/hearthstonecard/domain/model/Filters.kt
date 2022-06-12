package com.rai.hearthstonecard.domain.model

import kotlinx.serialization.Serializable

@Suppress("PLUGIN_IS_NOT_ENABLED")
@Serializable
data class Filters(val mana:String,val attack:String,val health:String):java.io.Serializable