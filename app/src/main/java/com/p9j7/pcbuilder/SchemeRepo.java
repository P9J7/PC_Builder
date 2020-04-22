package com.p9j7.pcbuilder;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

class SchemeRepo {
    private LiveData<List<Scheme>> allScheme;
    private SchemeDao schemeDao;

    public SchemeRepo(Context context) {
        SchemeDatabase schemeDatabase = SchemeDatabase.getDatabase(context.getApplicationContext());
        schemeDao = schemeDatabase.getSchemeDao();
        allScheme = schemeDao.getALLScheme();
    }

    public LiveData<List<Scheme>> getAllScheme() {
        return allScheme;
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
