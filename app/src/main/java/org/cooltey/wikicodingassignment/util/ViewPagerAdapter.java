package org.cooltey.wikicodingassignment.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.cooltey.wikicodingassignment.R;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

	private Activity mContext;
	private LayoutInflater mLayoutInflater;
	private ArrayList<String> mTitleData = new ArrayList<>();
	private ArrayList<String> mImageData = new ArrayList<>();
	private ImageLoader mImageLoader;
    private boolean mHQImg = true;

	public ViewPagerAdapter(Activity context, ArrayList<String> titleData, ArrayList<String> imageData, boolean hq) {
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mTitleData.addAll(titleData);
		mImageData.addAll(imageData);
        mImageLoader = WikiImageLoader.init(mContext);
        mHQImg = hq;
	}

	@Override
	public int getCount() {
		return mTitleData.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View itemView = mLayoutInflater.inflate(R.layout.item_gallery_view, container, false);

        final TouchImageView imageView = (TouchImageView) itemView.findViewById(R.id.img);
		final ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.img_progress);

        // get new img url
        String imgUrl = new ThumbnailHandler(mImageData.get(position)).getLargerImage();

        if(!mHQImg){
            imgUrl = mImageData.get(position);
        }

		// display image
		mImageLoader.displayImage(imgUrl, imageView, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
			}
		});

		container.addView(itemView);

		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((RelativeLayout) object);
	}
}
