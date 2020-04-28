package com.p9j7.pcbuilder.Data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.Model.Scheme;
import com.p9j7.pcbuilder.Model.SchemeWithParts;

import java.util.List;

public class SchemeViewModel extends AndroidViewModel {
    private SchemeRepo schemeRepo;
    private final MutableLiveData<SchemeWithParts> selected = new MutableLiveData<>();
    private final MutableLiveData<Part> partSelected = new MutableLiveData<>();

    public SchemeViewModel(@NonNull Application application) {
        super(application);
        schemeRepo = new SchemeRepo(application);
    }

    public MutableLiveData<Part> getPartSelected() {
        return partSelected;
    }

    public void partSelect(Part part) {
        partSelected.setValue(part);
    }

    public void select(SchemeWithParts scheme) {
        selected.setValue(scheme);
    }

    public LiveData<SchemeWithParts> getSelected() {
        return selected;
    }

    public LiveData<List<Scheme>> getAllScheme() {
        return schemeRepo.getAllScheme();
    }

    public LiveData<List<Part>> getAllPartBySchemeId(Integer schemeId) {
        return schemeRepo.getAllPartBySchemeId(schemeId);
    }

    public LiveData<List<SchemeWithParts>> getSchemesAndParts() {
        return schemeRepo.getSchemesAndParts();
    }


    void insertSchemes(Scheme scheme) {
        schemeRepo.insertSchemes(scheme);
    }
}
