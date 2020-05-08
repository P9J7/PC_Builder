package com.p9j7.pcbuilder.Data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Transaction;

import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.Model.Scheme;
import com.p9j7.pcbuilder.Model.SchemePartCrossRef;
import com.p9j7.pcbuilder.Model.SchemeWithParts;

import java.util.List;
import java.util.concurrent.ExecutionException;

class SchemeRepo {
    private SchemeDao schemeDao;

    public SchemeRepo(Context context) {
        PCBuilderDatabase schemeDatabase = PCBuilderDatabase.getDatabase(context.getApplicationContext());
        schemeDao = schemeDatabase.getSchemeDao();
    }


    public LiveData<List<SchemeWithParts>> getSchemesAndParts() {
        return schemeDao.getSchemesAndParts();
    }

    public LiveData<List<Part>> getPartsByCategory(String category) throws ExecutionException, InterruptedException {
        return new QueryAsyncTask(schemeDao).execute(category).get();
    }

    public LiveData<List<Part>> findPartsWithPatternAndCategory(String pattern, String category) {
        return schemeDao.findPartsWithPatternAndCategory("%" + pattern + "%", category);
    }

    @Transaction
    public void insertSchemeAndParts(Scheme scheme, List<Part> toSaveParts) {
        InsertAsyncTask schemeInsertTask = new InsertAsyncTask(schemeDao);
        try {
            List<Long> schemeId = schemeInsertTask.execute(scheme).get();
            toSaveParts.forEach(item -> new CrossRefInsertAsyncTask(schemeDao).execute(new SchemePartCrossRef(schemeId.get(0).intValue(), item.getPartId())));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Scheme scheme, List<Part> parts) {
        new DeleteAsyncTask(schemeDao).execute(scheme);
        parts.forEach(part -> {
            new CrossrefDeleteAsyncTask(schemeDao).execute(new SchemePartCrossRef(scheme.getSchemeId(), part.getPartId()));
        });
    }

    @Transaction
    public void updateSchemeBySchemeId(Scheme scheme, List<Part> toSaveParts, List<Part> toDeleteParts) {
        new SchemeUpdateAsyncTask(schemeDao).execute(scheme);
        toDeleteParts.forEach(part -> {
            new CrossrefDeleteAsyncTask(schemeDao).execute(new SchemePartCrossRef(scheme.getSchemeId(), part.getPartId()));
        });
        toSaveParts.forEach(part -> {
            new CrossRefInsertAsyncTask(schemeDao).execute(new SchemePartCrossRef(scheme.getSchemeId(), part.getPartId()));
        });
    }

    static class SchemeUpdateAsyncTask extends AsyncTask<Scheme, Void, Void> {
        private SchemeDao schemeDao;

        SchemeUpdateAsyncTask(SchemeDao schemeDao) {
            this.schemeDao = schemeDao;
        }

        @Override
        protected Void doInBackground(Scheme... schemes) {
            schemeDao.updateScheme(schemes);
            return null;
        }

    }

    static class CrossRefInsertAsyncTask extends AsyncTask<SchemePartCrossRef, Void, Void> {
        private SchemeDao schemeDao;

        CrossRefInsertAsyncTask(SchemeDao schemeDao) {
            this.schemeDao = schemeDao;
        }

        @Override
        protected Void doInBackground(SchemePartCrossRef... crossRefs) {
            schemeDao.insertCrossRef(crossRefs);
            return null;
        }

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

    static class InsertAsyncTask extends AsyncTask<Scheme, Void, List<Long>> {
        private SchemeDao schemeDao;

        InsertAsyncTask(SchemeDao schemeDao) {
            this.schemeDao = schemeDao;
        }


        @Override
        protected List<Long> doInBackground(Scheme... schemes) {
            return schemeDao.insertSchemes(schemes);
        }

    }

    static class DeleteAsyncTask extends AsyncTask<Scheme, Void, Void> {
        private SchemeDao schemeDao;

        public DeleteAsyncTask(SchemeDao schemeDao) {
            this.schemeDao = schemeDao;
        }

        @Override
        protected Void doInBackground(Scheme... schemes) {
            schemeDao.delete(schemes);
            return null;
        }
    }

    static class CrossrefDeleteAsyncTask extends AsyncTask<SchemePartCrossRef, Void, Void> {
        private SchemeDao schemeDao;

        public CrossrefDeleteAsyncTask(SchemeDao schemeDao) {
            this.schemeDao = schemeDao;
        }

        @Override
        protected Void doInBackground(SchemePartCrossRef... crossRefs) {
            schemeDao.deleteCrossRef(crossRefs);
            return null;
        }
    }
}
