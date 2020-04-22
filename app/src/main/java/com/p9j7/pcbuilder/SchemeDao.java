package com.p9j7.pcbuilder;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SchemeDao {
    @Query("SELECT * FROM Scheme ORDER BY ID DESC")
    LiveData<List<Scheme>> getALLScheme();

    @Insert
    void insertSchemes(Scheme... schemes);
}
