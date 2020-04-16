package com.example.daggerexamplefollowalong;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import com.example.daggerexamplefollowalong.models.User;
import com.example.daggerexamplefollowalong.ui.auth.AuthActivity;
import com.example.daggerexamplefollowalong.ui.auth.AuthResource;
import com.example.daggerexamplefollowalong.ui.main.MainActivity;
import dagger.android.support.DaggerAppCompatActivity;
import javax.inject.Inject;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObservers();
    }

    private void subscribeObservers() {
        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {
                    switch (userAuthResource.status) {
                        case LOADING:
                            break;
                        case AUTHENTICATED:
                            break;
                        case NOT_AUTHENTICATED:
                            break;
                        case ERROR:
                            navLoginScreen();
                            break;
                        default:

                    }
                }
            }
        });
    }



    private void navLoginScreen() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
