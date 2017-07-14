package org.cooltey.wikicodingassignment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.cooltey.wikicodingassignment.model.SearchResponse;
import org.cooltey.wikicodingassignment.model.SearchResponseItem;
import org.cooltey.wikicodingassignment.presenter.WebServicePresenter;
import org.cooltey.wikicodingassignment.presenter.WebServicePresenterCompl;
import org.cooltey.wikicodingassignment.util.RecyclerViewAdapter;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {


    private static final String LOG_TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void searchAction(String keywords){
        if(keywords != null && keywords.length() > 0) {
            // start web service
            WebServicePresenter webServicePresenter = new WebServicePresenterCompl();
            webServicePresenter.init();

            if (webServicePresenter != null) {

                List<String> keywordList = Arrays.asList(keywords.split("\\s*(=>|,|\\s)\\s*"));

                // do request
                webServicePresenter.doSearch(keywordList, new WebServicePresenter.ServiceCallback() {

                    @Override
                    public void success(@NonNull Call<SearchResponse> call, @NonNull List<SearchResponseItem> results) {
                        if (results != null) {

                            // setup adapter
                            mRecyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, results);
                            mRecyclerView.setAdapter(mRecyclerViewAdapter);
                        }
                    }

                    @Override
                    public void empty(@NonNull Call<SearchResponse> call){
                        // empty
                        Toast.makeText(getApplicationContext(), "No results, please try another keyword", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(@NonNull Call<SearchResponse> call, @NonNull String msg) {
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }
                });

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if(searchItem != null){
            searchView = (SearchView) searchItem.getActionView();
        }

        if(searchView != null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {

                    // search
                    searchAction(query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }




}
