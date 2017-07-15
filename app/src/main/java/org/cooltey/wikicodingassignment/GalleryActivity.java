package org.cooltey.wikicodingassignment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.cooltey.wikicodingassignment.util.DepthPageTransformer;
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

    private boolean mHQImage = true;

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
        if(mTitleData != null && mImageData != null) {
            mViewPagerAdapter = new ViewPagerAdapter(this, mTitleData, mImageData, mHQImage);
            mViewPager.setAdapter(mViewPagerAdapter);
            mViewPager.setPageTransformer(true, new DepthPageTransformer());
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
        }

        // show back button
        getSupportActionBar().setTitle(getKeyword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.gallery_menu, menu);
        CheckBox checkBox = (CheckBox) menu.findItem(R.id.action_hq).getActionView();
        checkBox.setText(getString(R.string.hq_img));
        checkBox.setChecked(true);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                int getCurrentIndex = mViewPager.getCurrentItem();

                mViewPagerAdapter = new ViewPagerAdapter(GalleryActivity.this, mTitleData, mImageData, b);
                mViewPager.setAdapter(mViewPagerAdapter);
                mViewPager.setCurrentItem(getCurrentIndex);

                mHQImage = b;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
