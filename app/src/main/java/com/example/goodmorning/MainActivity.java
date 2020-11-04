package com.example.goodmorning;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity {
    EditText edit;
    String txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = findViewById(R.id.edittext);

        int i = 1;

        Intent intent = getIntent();
        i = intent.getIntExtra("key",1);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        Pageadapter pageAdapter = new Pageadapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(i);
    }

}
