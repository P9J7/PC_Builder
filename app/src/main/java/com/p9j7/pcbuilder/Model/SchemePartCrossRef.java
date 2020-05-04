package com.p9j7.pcbuilder.Model;

import androidx.room.Entity;

@Entity(tableName = "crossref", primaryKeys = {"schemeId", "partId"})
public class SchemePartCrossRef {
    private int schemeId;
    private int partId;

    public SchemePartCrossRef(int schemeId, int partId) {
        this.schemeId = schemeId;
        this.partId = partId;
    }

    public int getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(int schemeId) {
        this.schemeId = schemeId;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }
}
