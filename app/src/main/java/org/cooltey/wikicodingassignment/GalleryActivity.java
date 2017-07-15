package org.cooltey.wikicodingassignment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.cooltey.wikicodingassignment.util.RecyclerViewAdapter;
import org.cooltey.wikicodingassignment.util.ViewPagerAdapter;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {


    private static final String LOG_TAG = "GalleryActivity";

    private ArrayList<String> mTitleData;
    private ArrayList<String> mImageData;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private TextView mCapTitleView;
    private TextView mCapCountView;
    private RelativeLayout mBottomLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // get data
        mTitleData = this.getIntent().getStringArrayListExtra(RecyclerViewAdapter.PASS_DATA_TITLE);
        mImageData = this.getIntent().getStringArrayListExtra(RecyclerViewAdapter.PASS_DATA_IMAGE);
        int currentPosition = this.getIntent().getIntExtra(RecyclerViewAdapter.PASS_DATA_POSITION, 0);
        String getKeyword = this.getIntent().getStringExtra(RecyclerViewAdapter.PASS_DATA_KEYWORD);

        // setup view
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mCapTitleView = (TextView) findViewById(R.id.cap_title);
        mCapCountView = (TextView) findViewById(R.id.cap_count);
        mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);

        // setup adapter
        mViewPagerAdapter = new ViewPagerAdapter(this, mTitleData, mImageData);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(currentPosition);

        // init
        mCapTitleView.setText(mTitleData.get(currentPosition));
        mCapCountView.setText(currentPosition + 1 + " / " + mTitleData.size());

        // setup listener
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // update cap contents
                mCapTitleView.setText(mTitleData.get(position));
                mCapCountView.setText(position + 1 + " / " + mTitleData.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // show back button
        getSupportActionBar().setTitle(getKeyword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
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
