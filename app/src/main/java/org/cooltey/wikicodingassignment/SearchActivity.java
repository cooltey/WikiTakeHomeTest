package org.cooltey.wikicodingassignment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.cooltey.wikicodingassignment.util.DatabaseHelper;
import org.cooltey.wikicodingassignment.util.SearchHistoryAdapter;

public class SearchActivity extends AppCompatActivity {


    private static final String LOG_TAG = "SearchActivity";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private SearchHistoryAdapter mSearchHistoryAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DatabaseHelper mDatabaseHelper;


    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // init database
        mDatabaseHelper = new DatabaseHelper(this);

        // setup view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // setup adapter
        mSearchHistoryAdapter = new SearchHistoryAdapter(this, mDatabaseHelper.getKeywords());
        mRecyclerView.setAdapter(mSearchHistoryAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLACK);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSearchHistoryAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });



        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // show back button
        getSupportActionBar().setTitle(getString(R.string.search_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);
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

                    // update database
                    mDatabaseHelper.insertKeyword(query);

                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.SEARCH_KEYWORD, query);

                    // go back
                    setResult(RESULT_OK, intent);

                    finish();

                    return true;
                }
            };

            searchView.setOnQueryTextListener(queryTextListener);
            searchView.onActionViewExpanded();
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK);
                finish();
                break;

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
