package com.example.manyepay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.manyepay.listnotesfragment.ListNotesFragment;
import com.example.manyepay.listnotesfragment.OnBackPressed;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new ListNotesFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.containerLayout);
        if (!(fragment instanceof OnBackPressed) || !((OnBackPressed) fragment).onBackPress()) {
            super.onBackPressed();
        }
    }
}