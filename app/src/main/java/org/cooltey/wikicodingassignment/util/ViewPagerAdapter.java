package org.cooltey.wikicodingassignment.util;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.cooltey.wikicodingassignment.R;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

	private Activity mContext;
	private LayoutInflater mLayoutInflater;
	private ArrayList<String> mTitleData = new ArrayList<>();
	private ArrayList<String> mImageData = new ArrayList<>();
	private ImageLoader mImageLoader;

	public ViewPagerAdapter(Activity context, ArrayList<String> titleData, ArrayList<String> imageData) {
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mTitleData.addAll(titleData);
		mImageData.addAll(imageData);
        mImageLoader = WikiImageLoader.init(mContext);
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

		TouchImageView imageView = (TouchImageView) itemView.findViewById(R.id.img);
		TextView titleView = (TextView) itemView.findViewById(R.id.title);

		// display image
		mImageLoader.displayImage(mImageData.get(position) , imageView);

		// display title
		titleView.setText(mTitleData.get(position));

		container.addView(itemView);

		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((RelativeLayout) object);
	}
}
