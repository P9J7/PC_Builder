package com.p9j7.pcbuilder.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.Model.Scheme;
import com.p9j7.pcbuilder.Model.SchemePartCrossRef;
import com.p9j7.pcbuilder.Model.SchemeWithParts;

import java.util.List;

@Dao
public interface SchemeDao {
    @Query("SELECT * FROM Scheme ORDER BY schemeId DESC")
    LiveData<List<Scheme>> getAllScheme();

    @Insert
    List<Long> insertSchemes(Scheme... schemes);

    @Transaction
    @Query("SELECT * FROM Scheme ORDER BY schemeId DESC")
    LiveData<List<SchemeWithParts>> getSchemesAndParts();

    @Transaction
    @Query("SELECT * FROM Part WHERE category = :category")
    LiveData<List<Part>> getPartsByCategory(String category);

    @Query("SELECT * FROM Part WHERE category = :category AND title LIKE :pattern")
    LiveData<List<Part>> findPartsWithPatternAndCategory(String pattern, String category);

    @Insert
    void insertCrossRef(SchemePartCrossRef... schemePartCrossRefs);
}