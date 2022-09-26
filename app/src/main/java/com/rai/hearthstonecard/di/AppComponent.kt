package com.rai.hearthstonecard.di

import android.content.Context
import com.rai.hearthstonecard.data.di.DataModule
import com.rai.hearthstonecard.ui.cardDetail.DetailCardFragment
import com.rai.hearthstonecard.ui.cardList.ListCardFragment
import com.rai.hearthstonecard.ui.city.MapInfoFragment
import com.rai.hearthstonecard.ui.classes.ClassPersonFragment
import com.rai.hearthstonecard.ui.map.MapCityFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {
    fun inject(fragment: DetailCardFragment)
    fun inject(fragment: ListCardFragment)
    fun inject(fragment: MapInfoFragment)
    fun inject(fragment: ClassPersonFragment)
    fun inject(fragment: MapCityFragment)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun context(context: Context):Builder

        fun build():AppComponent
    }
}