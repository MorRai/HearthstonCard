package com.rai.hearthstonecard.koin

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.rai.hearthstonecard.viewmodels.PersonClassViewModel
import com.rai.hearthstonecard.viewmodels.DetailCardViewModel
import com.rai.hearthstonecard.viewmodels.ListCardViewModel

val viewModelsModule = module {
    viewModelOf(::ListCardViewModel)
    viewModelOf(::DetailCardViewModel)
    viewModelOf(::PersonClassViewModel)
}