package org.cooltey.wikicodingassignment.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.cooltey.wikicodingassignment.Constants;
import org.cooltey.wikicodingassignment.model.SearchResponse;
import org.cooltey.wikicodingassignment.model.SearchResponseItem;
import org.cooltey.wikicodingassignment.util.WikiWebService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Web Service Presenter
 */

public class WebServicePresenterCompl implements WebServicePresenter{

    private static final String LOG_ERROR_TAG = "Search Response Error";

    private WikiWebService mWikiWebService;

    @Override
    public void init() {
        // Create Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create()) // add converter
                .baseUrl(Constants.WIKIPEDIA_URL) // add base url
                .build();


        // create web service
        mWikiWebService = retrofit.create(WikiWebService.class);
    }

    // do the query and get responses
    @Override
    public void doSearch(List<String> keywords, @NonNull final ServiceCallback callback) {
        if(mWikiWebService != null) {
            final Call<SearchResponse> responseCall = mWikiWebService.request(TextUtils.join(",", keywords));

            responseCall.enqueue(new Callback<SearchResponse>() {
                @Override
                public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {

                    if (response.body().success()) {

                        // remove empty images
                        List<SearchResponseItem> newList = new ArrayList<>();

                        for(SearchResponseItem item : response.body().query().pages()){
                            if(item.thumbUrl() != null){
                                newList.add(item);
                            }
                        }

                        if(newList.size() == 0){

                            callback.empty(responseCall);
                        }else{

                            callback.success(responseCall, newList);
                        }


                    }else if(response.body().hasError()){
                        callback.failure(responseCall, response.body().getError().getDetails());

                    }else{
                        callback.empty(responseCall);
                    }
                }

                @Override
                public void onFailure(Call<SearchResponse> call, Throwable t) {
                    Log.d(LOG_ERROR_TAG, t.toString());
                    Log.d(LOG_ERROR_TAG, call.toString());


                    callback.failure(responseCall, call.toString());
                }
            });
        }
    }

}
