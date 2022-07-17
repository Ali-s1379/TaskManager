package com.example.taskmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.taskmanager.models.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FloatingActionButton floatingActionButton;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private TextView textViewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();

        setupAdapter();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialogFragment fragment = AddDialogFragment.newInstance();
                fragment.show(getSupportFragmentManager(),"dialog");
            }
        });
    }

    private void initUi() {
        viewPager = findViewById(R.id.view_pager);
        floatingActionButton = findViewById(R.id.floating_action);
        tabLayout = findViewById(R.id.tab);
        textViewUser = findViewById(R.id.textView_user);
        textViewUser.setText(Repository.getInstance().getCurrentUser().getUsername());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
//            case R.id.delete_all:
//                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                builder.setCancelable(false);
//                builder.setTitle("Delete All Tasks?");
//                builder.setMessage("Warning: The Change Is Permanent");
//                builder.setPositiveButton("Confirm",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Repository.getInstance().deleteAll();
//                            }
//                        });
//                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//                break;
            case R.id.sign_out:
//                Intent intent = new Intent(this, LoginActivity.class);
                finish();
//                startActivity(intent);
        }

        return true;
    }
    public void setupAdapter() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ToBeDoneListFragment.newInstance(),"To Be Done");
        adapter.addFragment(InProgressListFragment.newInstance(),"In Progress");
        adapter.addFragment(DoneListFragment.newInstance(),"Done");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
//    public static void updateAdaptor() {
//        adapter.notifyDataSetChanged();
//    }
}
