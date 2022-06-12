package com.rai.hearthstonecard.koin

import com.rai.hearthstonecard.viewmodels.DetailCardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailCardViewModelModule = module {
    viewModel { DetailCardViewModel(get()) }
}