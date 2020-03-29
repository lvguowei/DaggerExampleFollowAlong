package com.example.daggerexamplefollowalong.di;

import androidx.lifecycle.ViewModelProvider;

import com.example.daggerexamplefollowalong.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bind(ViewModelProviderFactory factory);
}
