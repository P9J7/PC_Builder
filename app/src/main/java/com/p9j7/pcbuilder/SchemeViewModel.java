package com.p9j7.pcbuilder;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SchemeViewModel extends AndroidViewModel {
    private SchemeRepo schemeRepo;

    public SchemeViewModel(@NonNull Application application) {
        super(application);
        schemeRepo = new SchemeRepo(application);
    }

    LiveData<List<Scheme>> getAllScheme() {
        return schemeRepo.getAllScheme();
    }


    void insertSchemes(Scheme scheme) {
        schemeRepo.insertSchemes(scheme);
    }
}
