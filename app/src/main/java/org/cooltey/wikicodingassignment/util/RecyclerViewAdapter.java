package org.cooltey.wikicodingassignment.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.cooltey.wikicodingassignment.Constants;
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
    private ArrayList<String> mTitleData = new ArrayList<>();;
    private ArrayList<String> mImageData = new ArrayList<>();;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View cardView;
        public ViewHolder(View v) {
            super(v);
            cardView = v;
        }
    }

    public RecyclerViewAdapter(Activity context, List<SearchResponseItem> data) {
        mContext = context;
        mData = data;
        mImageLoader = WikiImageLoader.init(mContext);

        // for intent to activity
        transData();
    }

    public void transData(){
        for(SearchResponseItem rowData : mData){
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

        // setup title
        titleView.setText(mData.get(position).title());

        // display image
        mImageLoader.displayImage(mData.get(position).thumbUrl(), imageView);


        // setup long press event
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View view) {

                // trigger the popup dialog
                AlertDialog.Builder mAlterDialog = new AlertDialog.Builder(mContext);

                // get dialog view
                View dialogView = mContext.getLayoutInflater().inflate(R.layout.dialog_preview, null);
                ImageView dialogImageView = (ImageView) dialogView.findViewById(R.id.image_view);

                // setup
                mDialog = mAlterDialog.setView(dialogView).create();

                // the other way to present dialog
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(mDialog.getWindow().getAttributes());

                // get width and make it square
                DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
                float dpWidth = displayMetrics.widthPixels;
                float dpHeight = displayMetrics.heightPixels;

                // check orientation
                if(mContext.getResources().getConfiguration().orientation ==
                        mContext.getResources().getConfiguration().ORIENTATION_LANDSCAPE){

                    // fixed edge
                    layoutParams.width = (int) dpHeight - Constants.ORIENTATION_ADJUST;
                    layoutParams.height = (int) dpHeight - Constants.ORIENTATION_ADJUST;

                }else{

                    // fixed edge
                    layoutParams.width = (int) dpWidth;
                    layoutParams.height = (int) dpWidth;
                }

                mDialog.show();

                // show dialog with attributes
                mDialog.getWindow().setAttributes(layoutParams);
                // animation
                mDialog.getWindow().setWindowAnimations(R.style.DialogTheme);

                // show view
                mImageLoader.displayImage(mData.get(position).thumbUrl(), dialogImageView);



                return true;
            }


        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, GalleryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putStringArrayListExtra("title", mTitleData);
                intent.putStringArrayListExtra("image", mImageData);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });

        holder.cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP ||
                   motionEvent.getAction() == MotionEvent.ACTION_CANCEL){
                        // close dialog
                        if(mDialog != null && mDialog.isShowing()){
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
