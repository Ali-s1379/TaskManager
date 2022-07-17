package com.example.taskmanager;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanager.models.Repository;
import com.example.taskmanager.models.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    public static final int REQUEST_CODE = 777;
    private TextView textViewUsername;
    private TextView textViewPassword;
    private String username;
    private String password;
    private Button buttonLogin;
    private Button buttonRegister;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User user1 = new User("ali", "abcd");
        try {
            Repository.getInstance().addUser(user1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initUi(view);
        setListener();

    }

    private void initUi(@NonNull View view) {
        textViewUsername = view.findViewById(R.id.editText_username);
        textViewPassword = view.findViewById(R.id.editText_password);
        buttonLogin = view.findViewById(R.id.button_login);
        buttonRegister = view.findViewById(R.id.button_register);
    }

    private void setListener() {
        textViewUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                username = textViewUsername.getText().toString();
            }
        });
        textViewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = textViewPassword.getText().toString();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Repository.getInstance().login(username, password)) {
                    loginToast(true);
                    getFragmentManager().popBackStack();
                    startMainActivity();
                } else loginToast(false);
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity();
            }
        });
    }

    public void setUserPass (String user, String password) {
        textViewUsername.setText(user);
        textViewPassword.setText(password);
        this.username = (user);
        this.password = (password);
    }


    private void loginToast(boolean state) {
        if (state) {
            Toast.makeText(getContext(), "Successful Login", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getContext(), "Wrong Username Or Password", Toast.LENGTH_SHORT).show();
    }

    private void startRegisterActivity() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.login_fragment_container,
                        RegisterFragment.newInstance())
                .commit();
    }
    private void startMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }
}