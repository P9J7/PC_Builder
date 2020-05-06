package com.p9j7.pcbuilder.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.Model.Scheme;
import com.p9j7.pcbuilder.Model.SchemePartCrossRef;


// TODO 每次添加一个entity类，一定要记得在这里加上！！！浪费了N个小时！靠！
@Database(entities = {Scheme.class, Part.class, SchemePartCrossRef.class}, version = 1, exportSchema = false)
public abstract class PCBuilderDatabase extends RoomDatabase {
    private static PCBuilderDatabase INSTANCE;
    static synchronized PCBuilderDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PCBuilderDatabase.class,"PC_Builder_database")
                    .createFromAsset("lastdata.db")
                    .build();
        }
        return INSTANCE;
    }

    public abstract SchemeDao getSchemeDao();
}
