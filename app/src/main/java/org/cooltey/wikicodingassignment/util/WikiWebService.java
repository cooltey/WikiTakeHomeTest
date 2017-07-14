package org.cooltey.wikicodingassignment.util;


import android.support.annotation.NonNull;

import org.cooltey.wikicodingassignment.Constants;
import org.cooltey.wikicodingassignment.model.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Web Service for Search Images
 */

public interface WikiWebService {

    @GET("w/api.php?action=query&prop=pageimages&format=json&piprop=thumbnail&generator=prefixsearch&formatversion=2"
            + "&pilimit=" + Constants.PREFERRED_QUERY_NUMBERS
            + "&gpslimit=" + Constants.PREFERRED_QUERY_NUMBERS
            + "&pithumbsize=" + Constants.PREFERRED_THUMB_SIZE)

    Call<SearchResponse> request(
            @NonNull @Query("gpssearch") String titles
    );
}
