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
import com.p9j7.pcbuilder.Model.SchemePartCrossRef;


// TODO 每次添加一个entity类，一定要记得在这里加上！！！浪费了N个小时！靠！
@Database(entities = {Scheme.class, Part.class, SchemePartCrossRef.class}, version = 1, exportSchema = false)
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
                            scheme.put("schemeId", 2);
                            scheme.put("name", "AMD游戏平台");
                            scheme.put("price", 2999);
                            scheme.put("detail", "r5-3700+b460m+g903+16GDDR4");
                            db.insert("scheme", OnConflictStrategy.REPLACE, scheme);
                            ContentValues part = new ContentValues();
                            part.put("partId", 1);
                            part.put("price", 999.0);
                            part.put("title", "i7-9700k");
                            part.put("category", "cpu");
                            part.put("imgPath", "http://img12.360buyimg.com/n1/s450x450_jfs/t27469/203/1025585900/193714/6bfa61b2/5bbf43e7Ndd80aff7.jpg");
                            db.insert("part", OnConflictStrategy.REPLACE, part);
                            part.put("partId", 2);
                            part.put("price", 888.0);
                            part.put("title", "b450m pro");
                            part.put("category", "dcard");
                            part.put("imgPath", "http://img11.360buyimg.com/n1/s450x450_jfs/t1/107974/30/2052/559930/5e03141aE3fc51c13/2bf443c7063f6b67.jpg");
                            db.insert("part", OnConflictStrategy.REPLACE, part);
                            part.put("partId", 3);
                            part.put("price", 699.0);
                            part.put("title", "r7-3700");
                            part.put("category", "cpu");
                            part.put("imgPath", "http://img13.360buyimg.com/n1/s450x450_jfs/t1/56735/13/3407/522060/5d12db81Ec6a962a1/97924bcf518cd28a.jpg");
                            db.insert("part", OnConflictStrategy.REPLACE, part);
                            part.put("partId", 4);
                            part.put("price", 1699.0);
                            part.put("title", "rx-580");
                            part.put("category", "dcard");
                            part.put("imgPath", "http://img13.360buyimg.com/n1/s450x450_jfs/t1/92400/11/3459/303467/5de0791aE86bc4677/daa6ab60c68a841b.jpg");
                            db.insert("part", OnConflictStrategy.REPLACE, part);
                            ContentValues ref = new ContentValues();
                            ref.put("schemeId", 1);
                            ref.put("partId", 1);
                            db.insert("crossref", OnConflictStrategy.REPLACE, ref);
                            ref.put("schemeId", 1);
                            ref.put("partId", 2);
                            db.insert("crossref", OnConflictStrategy.REPLACE, ref);
                            ref.put("schemeId", 2);
                            ref.put("partId", 3);
                            db.insert("crossref", OnConflictStrategy.REPLACE, ref);
                            ref.put("schemeId", 2);
                            ref.put("partId", 4);
                            db.insert("crossref", OnConflictStrategy.REPLACE, ref);
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
