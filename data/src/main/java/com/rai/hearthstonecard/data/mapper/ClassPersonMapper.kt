package com.rai.hearthstonecard.data.mapper

import com.rai.hearthstonecard.data.model.ClassPersonDTO
import com.rai.hearthstonecard.data.model.ClassPersonEntity
import com.rai.hearthstonecard.domain.model.ClassPerson


internal fun List<ClassPersonDTO>.toDomainModels() : List<ClassPerson>{
    return map { it.toDomainModels() }
}


internal fun ClassPersonDTO.toDomainModels() : ClassPerson{
    return ClassPerson(
        id = id,
        name = name,
        image = image,
        slug = slug,
        cardId = cardId
    )
}


internal fun ClassPerson.toDomainModels() : ClassPersonEntity{
    return ClassPersonEntity(
        id = id,
        name = name,
        image = image,
        slug = slug,
        cardId = cardId
    )
}

internal fun ClassPersonEntity.toDomainModels() : ClassPerson{
    return ClassPerson(
        id = id,
        name = name,
        image = image,
        slug = slug,
        cardId = cardId
    )
}

