package com.erikantonyan.architecturecomponenets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.erikantonyan.architecturecomponenets.mvc.MVCActivity;
import com.erikantonyan.architecturecomponenets.mvp.MVPActivity;
import com.erikantonyan.architecturecomponenets.mvvm.MVVMActivity;

public class MainActivity extends AppCompatActivity {
    private Button mvcButton;
    private Button mvpButton;
    private Button mvvmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFields();
        setClickListeners();
    }

    public void onMVC() {
        startActivity(MVCActivity.getIntent(this));
    }

    public void onMVP() {
        startActivity(MVPActivity.getIntent(this));
    }

    public void onMVVM() {
        startActivity(MVVMActivity.getIntent(this));
    }

    private void initFields() {
        mvcButton = findViewById(R.id.button_mvc);
        mvpButton = findViewById(R.id.button_mvp);
        mvvmButton = findViewById(R.id.button_mvvm);
    }

    public void setClickListeners() {
        mvcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMVC();
            }
        });

        mvpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMVP();
            }
        });

        mvvmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMVVM();
            }
        });
    }
}
