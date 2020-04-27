package com.p9j7.pcbuilder.Data;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.Model.Scheme;

@Database(entities = {Scheme.class, Part.class}, version = 1, exportSchema = false)
public abstract class PCBuilderDatabase extends RoomDatabase {
    private static PCBuilderDatabase INSTANCE;
    static synchronized PCBuilderDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PCBuilderDatabase.class,"PC_Builder_database")
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            db.beginTransaction();
                            ContentValues scheme = new ContentValues();
                            scheme.put("schemeId", 1);
                            scheme.put("name", "Intel游戏平台");
                            scheme.put("price", 3699);
                            scheme.put("detail", "i7-9700k+b450m pro+g302+32GDDR4");
                            db.insert("scheme", OnConflictStrategy.REPLACE, scheme);
//                            scheme.clear();
                            scheme.put("schemeId", 2);
                            scheme.put("name", "AMD游戏平台");
                            scheme.put("price", 2999);
                            scheme.put("detail", "r5-3700+b460m+g903+16GDDR4");
                            db.insert("scheme", OnConflictStrategy.REPLACE, scheme);
                            ContentValues part = new ContentValues();
                            part.put("partId", 1);
                            part.put("schemeId", 1);
                            part.put("price", 999.0);
                            part.put("title", "i7-9700k");
                            db.insert("part", OnConflictStrategy.REPLACE, part);
//                            part.clear();
                            part.put("partId", 2);
                            part.put("schemeId", 1);
                            part.put("price", 888.0);
                            part.put("title", "b450m pro");
                            db.insert("part", OnConflictStrategy.REPLACE, part);
//                            part.clear();
                            part.put("partId", 3);
                            part.put("schemeId", 2);
                            part.put("price", 699.0);
                            part.put("title", "r5-3700");
                            db.insert("part", OnConflictStrategy.REPLACE, part);
//                            part.clear();
                            part.put("partId", 4);
                            part.put("schemeId", 2);
                            part.put("price", 1699.0);
                            part.put("title", "rx-580");
                            db.insert("part", OnConflictStrategy.REPLACE, part);
                            db.setTransactionSuccessful();
                            db.endTransaction();
                        }
                    })
                    .build();
        }
        return INSTANCE;
    }

    public abstract SchemeDao getSchemeDao();
}
