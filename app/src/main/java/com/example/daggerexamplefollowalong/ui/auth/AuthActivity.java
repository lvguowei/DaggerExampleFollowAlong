package com.example.daggerexamplefollowalong.ui.auth;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.RequestManager;
import com.example.daggerexamplefollowalong.R;
import com.example.daggerexamplefollowalong.models.User;
import com.example.daggerexamplefollowalong.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    private static final String TAG = AuthActivity.class.getSimpleName();

    private AuthViewModel viewModel;

    private EditText userIdEditText;

    private ProgressBar progressBar;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager glideInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        userIdEditText = findViewById(R.id.user_id_input);

        progressBar = findViewById(R.id.progress_bar);

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        viewModel = new ViewModelProvider(this, viewModelProviderFactory).get(AuthViewModel.class);
        setLogo();

        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.user().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {
                    switch (userAuthResource.status) {
                        case LOADING:
                            showProgressBar(true);
                            break;
                        case AUTHENTICATED:
                            showProgressBar(false);
                            break;
                        case NOT_AUTHENTICATED:
                            showProgressBar(false);
                            break;
                        case ERROR:
                            showProgressBar(false);
                            Toast.makeText(AuthActivity.this, userAuthResource.message,
                                Toast.LENGTH_SHORT)
                                .show();
                            break;
                        default:

                    }
                }
            }
        });
    }

    private void showProgressBar(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void attemptLogin() {
        if (TextUtils.isEmpty(userIdEditText.getText().toString())) {
            return;
        }

        viewModel.authenticateWith(Integer.parseInt(userIdEditText.getText().toString()));
    }

    private void setLogo() {
        glideInstance.load(logo).into((ImageView) findViewById(R.id.login_logo));
    }
}
