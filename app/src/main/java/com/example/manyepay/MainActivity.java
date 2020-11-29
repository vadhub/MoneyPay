package com.example.manyepay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.manyepay.listnotesfragment.ListNotesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new ListNotesFragment()).commit();
    }
}