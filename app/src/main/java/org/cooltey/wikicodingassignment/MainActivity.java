package org.cooltey.wikicodingassignment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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
    private static final int SEARCH_CODE = 100;
    public static final String SEARCH_KEYWORD = "keyword";

    private RecyclerView mRecyclerView;
    private TextView mEmptyView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private String getKeyword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // setup view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView.setHasFixedSize(true);


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLACK);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchAction(getKeyword);
            }
        });

        // setup empty view
        if(mRecyclerViewAdapter == null){
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void searchAction(final String keywords){
        if(keywords != null && keywords.length() > 0) {

            mSwipeRefreshLayout.setRefreshing(true);

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

                            mEmptyView.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);

                            // setup adapter
                            mRecyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, results, keywords);
                            mRecyclerView.setAdapter(mRecyclerViewAdapter);

                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void empty(@NonNull Call<SearchResponse> call){
                        // empty
                        Toast.makeText(getApplicationContext(), "No results, please try another keyword", Toast.LENGTH_LONG).show();

                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(@NonNull Call<SearchResponse> call, @NonNull String msg) {
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });


            }else{
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }else{
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:

                // go to search activity
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SEARCH_CODE);

                return false;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case SEARCH_CODE:
                // do search
                if(data != null) {
                    getKeyword = data.getStringExtra(SEARCH_KEYWORD);
                    searchAction(getKeyword);
                }
            break;
        }
    }

}
