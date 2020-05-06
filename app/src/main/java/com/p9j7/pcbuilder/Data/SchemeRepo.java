package com.p9j7.pcbuilder.Data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.Model.Scheme;
import com.p9j7.pcbuilder.Model.SchemeWithParts;

import java.util.List;
import java.util.concurrent.ExecutionException;

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


    public LiveData<List<SchemeWithParts>> getSchemesAndParts() {
        return schemeDao.getSchemesAndParts();
    }

    public void insertSchemes(Scheme... schemes) {
        new InsertAsyncTask(schemeDao).execute(schemes);
    }

    public LiveData<List<Part>> getPartsByCategory(String category) throws ExecutionException, InterruptedException {
        return new QueryAsyncTask(schemeDao).execute(category).get();
    }

    public LiveData<List<Part>> findPartsWithPatternAndCategory(String pattern, String category) {
        return schemeDao.findPartsWithPatternAndCategory("%" + pattern + "%", category);
    }

    static class QueryAsyncTask extends AsyncTask<String, Void, LiveData<List<Part>>> {
        private SchemeDao schemeDao;

        public QueryAsyncTask(SchemeDao schemeDao) {
            this.schemeDao = schemeDao;
        }

        @Override
        protected LiveData<List<Part>> doInBackground(String... strings) {
            return schemeDao.getPartsByCategory(strings[0]);
        }
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
