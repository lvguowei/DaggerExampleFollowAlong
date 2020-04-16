package com.example.daggerexamplefollowalong.di;

import com.example.daggerexamplefollowalong.di.auth.AuthModule;
import com.example.daggerexamplefollowalong.di.auth.AuthViewModelsModule;
import com.example.daggerexamplefollowalong.ui.auth.AuthActivity;

import com.example.daggerexamplefollowalong.ui.main.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {AuthModule.class, AuthViewModelsModule.class})
    abstract AuthActivity contributeAuthActivity();

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();
}
