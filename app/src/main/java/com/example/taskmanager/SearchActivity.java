package com.example.taskmanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogFragmentSearch dialogFragmentSearch = DialogFragmentSearch.newInstance();
        fragmentManager.beginTransaction()
                .replace(R.id.container_search,dialogFragmentSearch)
                .commit();
    }
}
