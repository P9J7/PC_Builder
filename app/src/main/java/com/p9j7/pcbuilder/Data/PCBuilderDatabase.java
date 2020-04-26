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
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("name", "Intel游戏平台");
                            contentValues.put("price", 3699);
                            contentValues.put("detail", "i7-9700k+b450m pro+g302+32GDDR4");
                            db.insert("scheme", OnConflictStrategy.REPLACE, contentValues);
                        }
                    })
                    .build();
        }
        return INSTANCE;
    }

    public abstract SchemeDao getSchemeDao();
}
