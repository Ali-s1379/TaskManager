package com.example.taskmanager;


import android.content.Context;
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

import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    private Button buttonRegister;
    private TextView textViewUsername;
    private TextView textViewPassword;
    private String username;
    private String password;
    SendMessage SM;

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    public void setUserPass (String user, String password) {
        textViewUsername.setText(user);
        textViewPassword.setText(password);
        this.username = (user);
        this.password = (password);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewUsername = view.findViewById(R.id.editText_new_username);
        textViewPassword = view.findViewById(R.id.editText_new_password);

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
//                if (!textViewPassword.getText().equals(null)){
//                    password = textViewPassword.getText().toString();
//                }else
//                    password = null;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = textViewPassword.getText().toString();
            }
        });
        buttonRegister = view.findViewById(R.id.button);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username != null && password != null){
                    if (Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z]).{8,40})").matcher(password).matches()){
                        try {
                            User user = new User(username, password);
                            Repository.getInstance().addUser(user);
                            registerToast(true);
                            startMainActivity();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Password Not Acceptable", Toast.LENGTH_SHORT).show();
                            registerToast(false);
                        }
                    }else Toast.makeText(getContext(), "Password Not Acceptable", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(getContext(), "Required Fields Empty", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void registerToast(boolean state){
        if (state){
            Toast.makeText(getContext(), "User Successfully Registered", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(getContext(), "User Already Registered", Toast.LENGTH_SHORT).show();
    }
    public interface SendMessage {
        void sendData(String user,String pass);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            SM = (SendMessage) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
    private void startMainActivity(){
        SM.sendData(username,password);
    }
}
