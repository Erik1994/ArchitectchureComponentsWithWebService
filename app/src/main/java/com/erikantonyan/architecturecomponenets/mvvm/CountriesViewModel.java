package com.erikantonyan.architecturecomponenets.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.erikantonyan.architecturecomponenets.model.CountriesServices;
import com.erikantonyan.architecturecomponenets.model.Country;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountriesViewModel extends ViewModel {
    private final MutableLiveData<List<String>> coutries = new MutableLiveData<>();

    private final MutableLiveData<Boolean> coutryError = new MutableLiveData<>();

    private CountriesServices mServices;

    public CountriesViewModel() {
        mServices = new CountriesServices();
        fetchCountries();

    }

    public LiveData<List<String>> getCountrie() {
        return coutries;
    }

    public LiveData<Boolean> getCountryError() {
        return coutryError;
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
                        coutries.setValue(countryNmaes);
                        coutryError.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        coutryError.setValue(true);
                    }
                });

    }

    public void onRefresh() {
        fetchCountries();
    }
}
