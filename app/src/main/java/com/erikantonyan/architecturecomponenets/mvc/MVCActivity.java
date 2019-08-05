package com.erikantonyan.architecturecomponenets.mvc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.erikantonyan.architecturecomponenets.R;

import java.util.ArrayList;
import java.util.List;

public class MVCActivity extends AppCompatActivity {
    private List<String> listValues = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private ListView mList;
    private CountriesController mController;
    private Button retryButton;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvc);
        setTitle("MVC Activity");


        mController = new CountriesController(this);
        mList = findViewById(R.id.list);
        retryButton = findViewById(R.id.retryButton);
        mProgressBar = findViewById(R.id.progress);
        mAdapter = new ArrayAdapter<>(this,R.layout.row_layout,R.id.listText,listValues);
        mList.setAdapter(mAdapter);

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onRetry();
            }
        });

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MVCActivity.this, "You clicked "  + listValues.get(position) , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setValues(List<String> values) {
        listValues.clear();
        listValues.addAll(values);
        retryButton.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mList.setVisibility(View.VISIBLE);
        mAdapter.notifyDataSetChanged();

    }

    public void onRetry() {
        mController.onRefresh();
        mList.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

    }
    
    public void onError() {
        Toast.makeText(this, "PLease try later", Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.GONE);
        mList.setVisibility(View.GONE);
        retryButton.setVisibility(View.VISIBLE);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context,MVCActivity.class);
    }
}