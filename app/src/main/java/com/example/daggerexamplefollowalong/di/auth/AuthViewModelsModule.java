package com.example.daggerexamplefollowalong.di.auth;

import androidx.lifecycle.ViewModel;

import com.example.daggerexamplefollowalong.di.ViewModelKey;
import com.example.daggerexamplefollowalong.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);
}
