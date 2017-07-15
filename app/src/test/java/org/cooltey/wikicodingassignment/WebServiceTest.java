package org.cooltey.wikicodingassignment;

import android.support.annotation.NonNull;

import org.cooltey.wikicodingassignment.model.SearchResponse;
import org.cooltey.wikicodingassignment.model.SearchResponseItem;
import org.cooltey.wikicodingassignment.presenter.WebServicePresenter;
import org.cooltey.wikicodingassignment.presenter.WebServicePresenterCompl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class WebServiceTest {

    private WebServicePresenter webService = new WebServicePresenterCompl();

    @Before
    public void setup(){
        webService.init();
    }

    @Test public void testRequestsSuccess() throws Throwable {

        List<String> tmpKeywords = new ArrayList<>();
        tmpKeywords.add("dog");
        tmpKeywords.add("cat");

        webService.doSearch(tmpKeywords, new WebServicePresenter.ServiceCallback() {
            @Override
            public void success(@NonNull Call<SearchResponse> call, @NonNull List<SearchResponseItem> results) {

                assertNotNull(call);
                assertNotNull(results);
                assertTrue(results.size() > 0);
            }

            @Override
            public void empty(@NonNull Call<SearchResponse> call) {

            }

            @Override
            public void failure(@NonNull Call<SearchResponse> call, @NonNull String msg) {

            }
        });
    }

    @Test public void testRequestsEmpty() throws Throwable {

        List<String> tmpKeywords = new ArrayList<>();
        tmpKeywords.add("one,two,three");

        webService.doSearch(tmpKeywords, new WebServicePresenter.ServiceCallback() {
            @Override
            public void success(@NonNull Call<SearchResponse> call, @NonNull List<SearchResponseItem> results) {

            }

            @Override
            public void empty(@NonNull Call<SearchResponse> call) {

                assertNotNull(call);
            }

            @Override
            public void failure(@NonNull Call<SearchResponse> call, @NonNull String msg) {

            }
        });
    }

    @Test public void testRequestsFailure() throws Throwable {

        List<String> tmpKeywords = new ArrayList<>();

        webService.doSearch(tmpKeywords, new WebServicePresenter.ServiceCallback() {
            @Override
            public void success(@NonNull Call<SearchResponse> call, @NonNull List<SearchResponseItem> results) {

            }

            @Override
            public void empty(@NonNull Call<SearchResponse> call) {

            }

            @Override
            public void failure(@NonNull Call<SearchResponse> call, @NonNull String msg) {

                assertNotNull(call);
                assertNotNull(msg);
            }
        });
    }
}