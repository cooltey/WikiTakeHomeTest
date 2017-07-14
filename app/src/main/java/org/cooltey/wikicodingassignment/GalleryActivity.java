package org.cooltey.wikicodingassignment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.cooltey.wikicodingassignment.util.ViewPagerAdapter;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {


    private static final String LOG_TAG = "GalleryActivity";

    private ArrayList<String> mTitleData;
    private ArrayList<String> mImageData;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // get data
        mTitleData = this.getIntent().getStringArrayListExtra("title");
        mImageData = this.getIntent().getStringArrayListExtra("image");
        int currentPosition = this.getIntent().getIntExtra("position", 0);

        // setup view
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        // setup adapter
        mViewPagerAdapter = new ViewPagerAdapter(this, mTitleData, mImageData);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(currentPosition);

        // show back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    // setup adapter


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
