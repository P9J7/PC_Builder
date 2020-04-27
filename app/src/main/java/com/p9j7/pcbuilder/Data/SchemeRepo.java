package com.p9j7.pcbuilder.Data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.Model.Scheme;
import com.p9j7.pcbuilder.Model.SchemeWithParts;

import java.util.List;

class SchemeRepo {
    private LiveData<List<Scheme>> allScheme;
    private SchemeDao schemeDao;

    public SchemeRepo(Context context) {
        PCBuilderDatabase schemeDatabase = PCBuilderDatabase.getDatabase(context.getApplicationContext());
        schemeDao = schemeDatabase.getSchemeDao();
        allScheme = schemeDao.getAllScheme();
    }

    public LiveData<List<Scheme>> getAllScheme() {
        return allScheme;
    }

    public LiveData<List<Part>> getAllPartBySchemeId(Integer schemeId) {
        return schemeDao.getAllPartBySchemeId(schemeId);
    }

    public LiveData<List<SchemeWithParts>> getSchemesAndParts() {
        return schemeDao.getSchemesAndParts();
    }

    public void insertSchemes(Scheme... schemes) {
        new InsertAsyncTask(schemeDao).execute(schemes);
    }

    static class InsertAsyncTask extends AsyncTask<Scheme, Void, Void> {
        private SchemeDao schemeDao;

        InsertAsyncTask(SchemeDao schemeDao) {
            this.schemeDao = schemeDao;
        }

        @Override
        protected Void doInBackground(Scheme... schemes) {
            schemeDao.insertSchemes(schemes);
            return null;
        }

    }
}
