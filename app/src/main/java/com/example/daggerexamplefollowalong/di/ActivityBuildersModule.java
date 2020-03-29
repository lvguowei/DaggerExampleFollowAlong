package com.example.daggerexamplefollowalong.di;

import com.example.daggerexamplefollowalong.di.auth.AuthModule;
import com.example.daggerexamplefollowalong.di.auth.AuthViewModelsModule;
import com.example.daggerexamplefollowalong.ui.auth.AuthActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {AuthModule.class, AuthViewModelsModule.class})
    abstract AuthActivity contribute();
}
