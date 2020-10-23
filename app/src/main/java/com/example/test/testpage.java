package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.widget.TextView;

public class testpage extends AppCompatActivity {
    int testId;
    String id;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testpage);

        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        testId = Integer.parseInt(id);

        textView.setText(id);
    }
}