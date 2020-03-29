package com.example.daggerexamplefollowalong.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.daggerexamplefollowalong.models.User;
import com.example.daggerexamplefollowalong.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;


public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    private final AuthApi authApi;

    private MediatorLiveData<User> authUser = new MediatorLiveData<>();

    @Inject
    AuthViewModel(AuthApi authApi) {
        this.authApi = authApi;
    }

    void authenticateWith(int userId) {
        final LiveData<User> source = LiveDataReactiveStreams.fromPublisher(authApi.getUser(userId).subscribeOn(Schedulers.io()));
        authUser.addSource(source, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                authUser.setValue(user);
                authUser.removeSource(source);
            }
        });
    }

    LiveData<User> user() {
        return authUser;
    }
}
