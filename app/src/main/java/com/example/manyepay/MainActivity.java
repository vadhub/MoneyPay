package com.example.manyepay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.manyepay.listnotesfragment.ListNotesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new ListNotesFragment()).commit();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        System.out.println(item.getItemId());
        if(item.getItemId() == android.R.id.home){
            getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new ListNotesFragment()).commit();
        }
        return super.onOptionsItemSelected(item);
    }

}