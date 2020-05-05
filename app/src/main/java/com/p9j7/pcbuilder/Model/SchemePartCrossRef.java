package com.p9j7.pcbuilder.Model;

import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "crossref", primaryKeys = {"schemeId", "partId"}, indices = {@Index("partId")})
public class SchemePartCrossRef {
    //    @NonNull
    private int schemeId;
    //    @NonNull
    private long partId;

    public SchemePartCrossRef(int schemeId, long partId) {
        this.schemeId = schemeId;
        this.partId = partId;
    }

    public int getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(int schemeId) {
        this.schemeId = schemeId;
    }

    public long getPartId() {
        return partId;
    }

    public void setPartId(long partId) {
        this.partId = partId;
    }
}
