package com.example.daggerexamplefollowalong.di;

import android.app.Application;

import com.example.daggerexamplefollowalong.BaseApplication;
import com.example.daggerexamplefollowalong.SessionManager;
import com.example.daggerexamplefollowalong.viewmodels.ViewModelProviderFactory;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBuildersModule.class,
        AppModule.class,
        ViewModelFactoryModule.class})
public interface AppComponent extends AndroidInjector<BaseApplication> {

    SessionManager sessionManager();

    @Component.Factory
    interface Factory {
        AppComponent create(@BindsInstance Application application);
    }
}
