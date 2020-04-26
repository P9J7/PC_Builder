package com.p9j7.pcbuilder.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.p9j7.pcbuilder.Model.Scheme;

@Database(entities = {Scheme.class}, version = 1, exportSchema = false)
public abstract class SchemeDatabase extends RoomDatabase {
    private static SchemeDatabase INSTANCE;
    static synchronized SchemeDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),SchemeDatabase.class,"scheme_database")
                    .build();
        }
        return INSTANCE;
    }

    public abstract SchemeDao getSchemeDao();
}
