package org.cooltey.wikicodingassignment.model;

import android.support.annotation.Nullable;

/**
 * Search Response Item Model
 */

public class ServiceError {

    @SuppressWarnings("unused") @Nullable private String code;
    @SuppressWarnings("unused") @Nullable private String info;
    @SuppressWarnings("unused") @Nullable private String docref;

    @Nullable public String getTitle() {
        return code;
    }

    @Nullable public String getDetails() {
        return info;
    }

    @Nullable public String getDocRef() {
        return docref;
    }

    public boolean badToken() {
        return "badtoken".equals(code);
    }

    @Override public String toString() {
        return "ServiceError{"
                + "code='" + code + '\''
                + ", info='" + info + '\''
                + ", docref='" + docref + '\''
                + '}';
    }
}