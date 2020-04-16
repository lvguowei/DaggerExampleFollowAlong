package com.example.daggerexamplefollowalong.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import com.example.daggerexamplefollowalong.SessionManager;
import com.example.daggerexamplefollowalong.models.User;
import com.example.daggerexamplefollowalong.network.auth.AuthApi;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;


public class AuthViewModel extends ViewModel {

    private final AuthApi authApi;

    private SessionManager sessionManager;

    @Inject
    AuthViewModel(AuthApi authApi, SessionManager sessionManager) {
        this.authApi = authApi;
        this.sessionManager = sessionManager;
    }

    void authenticateWith(int userId) {
        sessionManager.authenticateWithId(queryUserId(userId));
    }

    private LiveData<AuthResource<User>> queryUserId(int userId) {
        return LiveDataReactiveStreams.fromPublisher(
            authApi.getUser(userId)
                .onErrorReturn(new Function<Throwable, User>() {
                    @Override
                    public User apply(Throwable throwable) {
                        User errorUser = new User();
                        errorUser.setId(-1);
                        return errorUser;
                    }
                })
                .map(new Function<User, AuthResource<User>>() {
                    @Override
                    public AuthResource<User> apply(User user) {
                        if (user.getId() == -1) {
                            return AuthResource.error("Could not authenticate", null);
                        }
                        return AuthResource.authenticated(user);
                    }
                })
                .subscribeOn(Schedulers.io())

        );
    }

    LiveData<AuthResource<User>> observeAuthState() {
        return sessionManager.getAuthUser();
    }
}
