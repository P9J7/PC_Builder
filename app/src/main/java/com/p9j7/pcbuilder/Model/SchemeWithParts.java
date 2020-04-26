package com.p9j7.pcbuilder.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class SchemeWithParts {
    @Embedded
    private Scheme scheme;
    @Relation(parentColumn = "schemeId", entityColumn = "partId")
    private List<Part> parts;

    public SchemeWithParts(Scheme scheme, List<Part> parts) {
        this.scheme = scheme;
        this.parts = parts;
    }

    public Scheme getScheme() {
        return scheme;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
