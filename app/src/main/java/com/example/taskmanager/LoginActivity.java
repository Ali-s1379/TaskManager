package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity implements RegisterFragment.SendMessage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportFragmentManager().findFragmentById(R.id.login_fragment_container) == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.login_fragment_container,
                            LoginFragment.newInstance())
                    .commit();
        }

    }


    @Override
    public void sendData(String user,String pass) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.login_fragment_container,
                        LoginFragment.newInstance())
                .commit();

    }
}
