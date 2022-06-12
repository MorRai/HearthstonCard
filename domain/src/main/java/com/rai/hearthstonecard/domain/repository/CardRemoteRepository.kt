package com.rai.hearthstonecard.domain.repository

import com.rai.hearthstonecard.domain.model.Card
import com.rai.hearthstonecard.domain.model.ClassPerson

interface CardRemoteRepository {

    suspend fun getCard(id: Int): Result<Card>

    suspend fun getCards(page: Int, pageSize: Int, classSlug: String, mana:String, attack:String, health:String):Result<List<Card>>

    suspend fun getClasses():Result<List<ClassPerson>>

}