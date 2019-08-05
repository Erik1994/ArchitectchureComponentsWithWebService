package com.erikantonyan.architecturecomponenets.mvvm;

import android.arch.lifecycle.ViewModelProviders;
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
import com.erikantonyan.architecturecomponenets.mvp.CountriesPresenter;
import com.erikantonyan.architecturecomponenets.mvp.MVPActivity;

import java.util.ArrayList;
import java.util.List;

public class MVVMActivity extends AppCompatActivity {
    private List<String> listValues = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private ListView mList;
    private CountriesViewModel mViewModel;
    private Button retryButton;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm);
        setTitle("MVVM Activity");


        mViewModel = ViewModelProviders.of(this).get(CountriesViewModel.class);

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
                Toast.makeText(MVVMActivity.this, "You clicked "  + listValues.get(position) , Toast.LENGTH_SHORT).show();
            }
        });


        oserveViewModel();
    }

    private void oserveViewModel() {
        mViewModel.getCountrie().observe(this,countries ->{
            if(countries != null) {
                listValues.clear();
                listValues.addAll(countries);
                mList.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();

            } else {
                mList.setVisibility(View.GONE);
            }
        } );

        mViewModel.getCountryError().observe(this,error->{
            mProgressBar.setVisibility(View.GONE);
            if(error) {
                Toast.makeText(this, "PLease try later", Toast.LENGTH_SHORT).show();
                retryButton.setVisibility(View.VISIBLE);
            } else {
                retryButton.setVisibility(View.GONE);
            }
        });
    }

    public void onRetry() {
        mViewModel.onRefresh();
        mList.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MVVMActivity.class);
    }
}