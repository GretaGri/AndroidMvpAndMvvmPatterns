package com.enpassio.androidmvpandmvvmpatterns;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.view.MainActivity;

public class FirstActivity extends AppCompatActivity {

    Button mvpButton;
    Button mvvmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        mvpButton = findViewById(R.id.mvp_example_button);
        mvvmButton = findViewById(R.id.mvvm_example_button);


        mvpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mvvmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.view.MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
