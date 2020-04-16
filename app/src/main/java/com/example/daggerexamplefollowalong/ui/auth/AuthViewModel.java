package com.example.daggerexamplefollowalong.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.daggerexamplefollowalong.models.User;
import com.example.daggerexamplefollowalong.network.auth.AuthApi;

import io.reactivex.functions.Function;
import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;


public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    private final AuthApi authApi;

    private MediatorLiveData<AuthResource<User>> authUser = new MediatorLiveData<>();

    @Inject
    AuthViewModel(AuthApi authApi) {
        this.authApi = authApi;
    }

    void authenticateWith(int userId) {
        authUser.setValue(AuthResource.<User>loading(null));
        final LiveData<AuthResource<User>> source = LiveDataReactiveStreams.fromPublisher(
            authApi.getUser(userId)
                .onErrorReturn(new Function<Throwable, User>() {
                    @Override
                    public User apply(Throwable throwable) throws Exception {
                        User errorUser = new User();
                        errorUser.setId(-1);
                        return errorUser;
                    }
                })
                .map(new Function<User, AuthResource<User>>() {
                    @Override
                    public AuthResource<User> apply(User user) throws Exception {
                        if (user.getId() == -1) {
                            return AuthResource.error("Could not authenticate", null);
                        }
                        return AuthResource.authenticated(user);
                    }
                })
                .subscribeOn(Schedulers.io())

        );

        authUser.addSource(source, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> user) {
                authUser.setValue(user);
            }
        });
    }

    LiveData<AuthResource<User>> user() {
        return authUser;
    }
}
