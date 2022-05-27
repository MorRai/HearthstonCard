package com.rai.hearthstonecard.koin

import com.rai.hearthstonecard.viewmodels.PersonClassViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val personClassViewModelModule = module {
    viewModel { PersonClassViewModel(get()) }
}