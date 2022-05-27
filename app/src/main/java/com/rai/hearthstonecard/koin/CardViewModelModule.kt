package com.rai.hearthstonecard.koin

import com.rai.hearthstonecard.viewmodels.ListCardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listCardViewModelModule = module{
    viewModel {ListCardViewModel(get(),get()) }
}