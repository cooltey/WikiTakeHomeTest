package org.cooltey.wikicodingassignment.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.cooltey.wikicodingassignment.GalleryActivity;
import org.cooltey.wikicodingassignment.R;
import org.cooltey.wikicodingassignment.model.SearchResponseItem;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<SearchResponseItem> mData = new ArrayList<>();
    private ImageLoader mImageLoader;
    private Activity mContext;
    private Dialog mDialog;
    private ArrayList<String> mTitleData = new ArrayList<>();
    private ArrayList<String> mImageData = new ArrayList<>();
    private String mKeyword;
    private int mLastPosition = -1;

    public static final String PASS_DATA_IMAGE = "image";
    public static final String PASS_DATA_TITLE = "title";
    public static final String PASS_DATA_KEYWORD = "keyword";
    public static final String PASS_DATA_POSITION = "position";

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View cardView;

        public ViewHolder(View v) {
            super(v);
            cardView = v;
        }
    }

    public RecyclerViewAdapter(Activity context, List<SearchResponseItem> data, String keyword) {
        mContext = context;
        mData = data;
        mImageLoader = WikiImageLoader.init(mContext);
        mKeyword = keyword;

        // for intent to activity
        transData();

    }

    public void transData() {
        for (SearchResponseItem rowData : mData) {
            mTitleData.add(rowData.title());
            mImageData.add(rowData.thumbUrl());
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        TextView titleView = (TextView) holder.cardView.findViewById(R.id.card_text);
        ImageView imageView = (ImageView) holder.cardView.findViewById(R.id.card_img);
        final ProgressBar progressView = (ProgressBar) holder.cardView.findViewById(R.id.card_img_progress);

        // setup title
        titleView.setText(mData.get(position).title());

        // display image
        mImageLoader.displayImage(mData.get(position).thumbUrl(), imageView, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressView.setVisibility(View.GONE);
            }
        });


        // setup long press event
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {

                PopupImageHandler popupImageHandler = new PopupImageHandler(mContext, mDialog);

                // show view
                mImageLoader.displayImage(mData.get(position).thumbUrl(), popupImageHandler.getImageView());

                mDialog = popupImageHandler.getDialog();

                return true;
            }


        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, GalleryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putStringArrayListExtra(PASS_DATA_TITLE, mTitleData);
                intent.putStringArrayListExtra(PASS_DATA_IMAGE, mImageData);
                intent.putExtra(PASS_DATA_KEYWORD, mKeyword);
                intent.putExtra(PASS_DATA_POSITION, position);
                mContext.startActivity(intent);
            }
        });

        holder.cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP ||
                        motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    // close dialog
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
