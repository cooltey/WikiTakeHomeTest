package org.cooltey.wikicodingassignment.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Search Response Model
 */

public class SearchResponse {

    @SuppressWarnings("unused") @Nullable private ServiceError error;
    @SuppressWarnings("unused") @SerializedName("batchcomplete") private boolean batchComplete;
    @SuppressWarnings("unused") @SerializedName("continue") @Nullable private Map<String, String> continuation;
    @SuppressWarnings("unused") @Nullable private List<SearchResponseItem> pages;


    public boolean batchComplete() {
        return batchComplete;
    }

    @Nullable public Map<String, String> continuation() {
        return continuation;
    }

    @Nullable public List<SearchResponseItem> pages() {
        return pages;
    }

    @Nullable public ServiceError getError() {
        return error;
    }

    public boolean hasError() {
        return error != null;
    }

    @Nullable public String code() {
        return error != null ? error.getTitle() : null;
    }

    @Nullable public String info() {
        return error != null ? error.getDetails() : null;
    }

    public boolean success() {
        return error == null;
    }
}