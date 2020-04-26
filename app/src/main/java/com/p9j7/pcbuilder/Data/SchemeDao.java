package com.p9j7.pcbuilder.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.p9j7.pcbuilder.Model.Scheme;

import java.util.List;

@Dao
public interface SchemeDao {
    @Query("SELECT * FROM Scheme ORDER BY schemeId DESC")
    LiveData<List<Scheme>> getALLScheme();

    @Insert
    void insertSchemes(Scheme... schemes);
}
