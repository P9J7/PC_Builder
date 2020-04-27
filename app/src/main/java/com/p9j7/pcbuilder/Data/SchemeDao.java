package com.p9j7.pcbuilder.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.Model.Scheme;
import com.p9j7.pcbuilder.Model.SchemeWithParts;

import java.util.List;

@Dao
public interface SchemeDao {
    @Query("SELECT * FROM Scheme ORDER BY schemeId DESC")
    LiveData<List<Scheme>> getAllScheme();

    @Insert
    void insertSchemes(Scheme... schemes);

    @Query("SELECT * FROM Part WHERE schemeForeId = :schemeId")
    LiveData<List<Part>> getAllPartBySchemeId(Integer schemeId);

    @Transaction
    @Query("SELECT * FROM Scheme ORDER BY schemeId DESC")
    LiveData<List<SchemeWithParts>> getSchemesAndParts();
}