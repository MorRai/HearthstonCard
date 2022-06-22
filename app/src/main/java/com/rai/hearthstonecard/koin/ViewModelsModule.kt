package com.rai.hearthstonecard.koin

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.rai.hearthstonecard.ui.classes.PersonClassViewModel
import com.rai.hearthstonecard.ui.cardDetail.DetailCardViewModel
import com.rai.hearthstonecard.ui.cardList.ListCardViewModel
import com.rai.hearthstonecard.ui.map.MapCityViewModel
import com.rai.hearthstonecard.ui.city.MapInfoViewModel

val viewModelsModule = module {
    viewModelOf(::ListCardViewModel)
    viewModelOf(::DetailCardViewModel)
    viewModelOf(::PersonClassViewModel)
    viewModelOf(::MapCityViewModel)
    viewModelOf(::MapInfoViewModel)
}