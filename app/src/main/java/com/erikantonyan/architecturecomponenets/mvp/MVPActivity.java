package com.erikantonyan.architecturecomponenets.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.erikantonyan.architecturecomponenets.R;
import com.erikantonyan.architecturecomponenets.mvc.CountriesController;
import com.erikantonyan.architecturecomponenets.mvc.MVCActivity;

import java.util.ArrayList;
import java.util.List;

public class MVPActivity extends AppCompatActivity implements CountriesPresenter.View {

    private List<String> listValues = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private ListView mList;
    private CountriesPresenter mPresenter;
    private Button retryButton;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        setTitle("MVP Activity");

        mPresenter = new CountriesPresenter(this);

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
                Toast.makeText(MVPActivity.this, "You clicked "  + listValues.get(position) , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MVPActivity.class);
    }

    @Override
    public void setValues(List<String> countries) {
        listValues.clear();
        listValues.addAll(countries);
        retryButton.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mList.setVisibility(View.VISIBLE);
        mAdapter.notifyDataSetChanged();

    }

    public void onRetry() {
        mPresenter.onRefresh();
        mList.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void onError() {
        Toast.makeText(this, "PLease try later", Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.GONE);
        mList.setVisibility(View.GONE);
        retryButton.setVisibility(View.VISIBLE);

    }
}