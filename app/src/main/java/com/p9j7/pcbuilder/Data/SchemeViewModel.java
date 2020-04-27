package com.p9j7.pcbuilder.Data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.Model.Scheme;

import java.util.List;

public class SchemeViewModel extends AndroidViewModel {
    private SchemeRepo schemeRepo;
    private final MutableLiveData<Scheme> selected = new MutableLiveData<>();

    public SchemeViewModel(@NonNull Application application) {
        super(application);
        schemeRepo = new SchemeRepo(application);
    }

    public void select(Scheme scheme) {
        selected.setValue(scheme);
    }

    public LiveData<Scheme> getSelected() {
        return selected;
    }

    public LiveData<List<Scheme>> getAllScheme() {
        return schemeRepo.getAllScheme();
    }

    public LiveData<List<Part>> getAllPartBySchemeId(Integer schemeId) {
        return schemeRepo.getAllPartBySchemeId(schemeId);
    }


    void insertSchemes(Scheme scheme) {
        schemeRepo.insertSchemes(scheme);
    }
}
