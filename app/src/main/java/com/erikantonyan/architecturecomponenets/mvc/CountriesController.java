package com.erikantonyan.architecturecomponenets.mvc;

import android.view.View;

import com.erikantonyan.architecturecomponenets.model.CountriesServices;
import com.erikantonyan.architecturecomponenets.model.Country;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountriesController {
    private MVCActivity view;
    private CountriesServices mServices;


    public CountriesController(MVCActivity view)  {
        this.view = view;
        mServices = new CountriesServices();
        fetchCountries();

    }

    private void fetchCountries() {
        mServices.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Country>>() {
                    @Override
                    public void onSuccess(List<Country> value) {
                        List<String> countryNmaes = new ArrayList<>();
                        for(Country country : value) {
                            countryNmaes.add(country.countryName);
                        }
                        view.setValues(countryNmaes);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError();
                    }
                });

    }

    public void onRefresh() {
        fetchCountries();
    }




}
