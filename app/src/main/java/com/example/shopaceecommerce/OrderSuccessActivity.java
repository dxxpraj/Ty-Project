package com.example.shopaceecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class OrderSuccessActivity extends AppCompatActivity {

    TextView t1, t2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        t1 = findViewById(R.id.textView2);
        t2 = findViewById(R.id.textView3);

        String trackid = getIntent().getStringExtra("trackid");

        t2.setText("Tracking Id: "+trackid);
    }
}