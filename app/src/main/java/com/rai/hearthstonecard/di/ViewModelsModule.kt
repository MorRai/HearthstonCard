package com.rai.hearthstonecard.di

import androidx.lifecycle.ViewModel
import com.rai.hearthstonecard.ui.cardDetail.DetailCardViewModel
import com.rai.hearthstonecard.ui.cardList.ListCardViewModel
import com.rai.hearthstonecard.ui.city.MapInfoViewModel
import com.rai.hearthstonecard.ui.classes.PersonClassViewModel
import com.rai.hearthstonecard.ui.map.MapCityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {

    @Binds
    @ClassKey(DetailCardViewModel::class)
    @IntoMap
    abstract fun detailCardViewModel(detailCardViewModel: DetailCardViewModel) : ViewModel

    @Binds
    @ClassKey(ListCardViewModel::class)
    @IntoMap
    abstract fun listCardViewModel(listCardViewModel: ListCardViewModel) : ViewModel

    @Binds
    @ClassKey(MapInfoViewModel::class)
    @IntoMap
    abstract fun mapInfoViewModel(mapInfoViewModel: MapInfoViewModel) : ViewModel

    @Binds
    @ClassKey(PersonClassViewModel::class)
    @IntoMap
    abstract fun personClassViewModel(personClassViewModel: PersonClassViewModel) : ViewModel

    @Binds
    @ClassKey(MapCityViewModel::class)
    @IntoMap
    abstract fun mapCityViewModel(mapCityViewModel: MapCityViewModel) : ViewModel

}