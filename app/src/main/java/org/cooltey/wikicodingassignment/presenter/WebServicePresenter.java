package org.cooltey.wikicodingassignment.presenter;

import android.support.annotation.NonNull;

import org.cooltey.wikicodingassignment.model.SearchResponse;
import org.cooltey.wikicodingassignment.model.SearchResponseItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Web Service Presenter
 */

public interface WebServicePresenter {
    @NonNull void init();
    @NonNull void doSearch(ArrayList<String> keywords, ServiceCallback callback);
    @NonNull void clear();

    interface ServiceCallback {

        void success(@NonNull Call<SearchResponse> call,
                     @NonNull List<SearchResponseItem> results);

        void failure(@NonNull Call<SearchResponse> call,
                     @NonNull String msg);
    }
}
