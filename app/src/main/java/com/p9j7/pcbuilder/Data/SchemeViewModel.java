package com.p9j7.pcbuilder.Data;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.Model.Scheme;
import com.p9j7.pcbuilder.Model.SchemeWithParts;
import com.p9j7.pcbuilder.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SchemeViewModel extends AndroidViewModel {
    private  MutableLiveData<List<Object>> mixList;
    private SchemeRepo schemeRepo;
    private final MutableLiveData<SchemeWithParts> selected = new MutableLiveData<>();
    private final MutableLiveData<Part> partSelected = new MutableLiveData<>();
    private final MutableLiveData<String> pickTitle = new MutableLiveData<>();
    private Integer adapterPosition;
    private final List<Object> defaultPickTexts;
    private final Map<Integer, String> intToCategory;
    private Integer quickVisible;

    public SchemeViewModel(@NonNull Application application) {
        super(application);
        schemeRepo = new SchemeRepo(application);
        Context context = application.getApplicationContext();
        this.mixList = new MutableLiveData<>(new ArrayList<>(Arrays.asList(context.getString(R.string.pick_processor), context.getString(R.string.pick_motherboard), context.getString(R.string.pick_gpu),
                context.getString(R.string.pick_mem), context.getString(R.string.pick_storage), context.getString(R.string.pick_psu), context.getString(R.string.pick_casing), context.getString(R.string.pick_cooler))));
        // 这里必须new，否则引用的是同一个地址
        this.defaultPickTexts = new ArrayList<>(this.mixList.getValue());
        this.adapterPosition = 0;
        this.intToCategory = new HashMap<>();
        intToCategory.put(0, "cpu");
        intToCategory.put(1, "motherboard");
        intToCategory.put(2, "dcard");
        intToCategory.put(3, "ram");
        intToCategory.put(4, "storage");
        intToCategory.put(5, "psu");
        intToCategory.put(6, "casing");
        intToCategory.put(7, "cooler");
        this.quickVisible = View.GONE;
    }

    public Integer getQuickVisible() {
        return quickVisible;
    }

    public void setQuickVisible(Integer quickVisible) {
        this.quickVisible = quickVisible;
    }

    public Map<Integer, String> getIntToCategory() {
        return intToCategory;
    }

    public List<Object> getDefaultPickTexts() {
        return defaultPickTexts;
    }

    public MutableLiveData<List<Object>> getMixList() {
        return mixList;
    }

    public MutableLiveData<List<Object>> setMixListByIndex(int i, Object object) {
        List<Object> old = this.getMixList().getValue();
        old.set(i, object);
        this.mixList.setValue(old);
        return mixList;
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

    public LiveData<List<SchemeWithParts>> getSchemesAndParts() {
        return schemeRepo.getSchemesAndParts();
    }


    void insertSchemes(Scheme scheme) {
        schemeRepo.insertSchemes(scheme);
    }

    public void setPickTitle(String pickTitle) {
        this.pickTitle.setValue(pickTitle);
    }

    public MutableLiveData<String> getPickTitle() {
        return pickTitle;
    }

    public void setPickIndex(int adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    public Integer getAdapterPosition() {
        return adapterPosition;
    }

    public LiveData<List<Part>> getPartsByCategory(Integer adapterPosition) throws ExecutionException, InterruptedException {
        return schemeRepo.getPartsByCategory(intToCategory.get(adapterPosition));
    }

    public LiveData<List<Part>> findPartsWithPatternAndCategory(String pattern, String category) {
        return schemeRepo.findPartsWithPatternAndCategory(pattern, category);
    }
}
