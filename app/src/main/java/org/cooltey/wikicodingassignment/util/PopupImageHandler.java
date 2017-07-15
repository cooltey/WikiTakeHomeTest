package org.cooltey.wikicodingassignment.util;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import org.cooltey.wikicodingassignment.Constants;
import org.cooltey.wikicodingassignment.R;

/**
 * ThumbnailHandler
 */

public class PopupImageHandler {

    private Activity mContext;
    private Dialog mDialog;

    public PopupImageHandler(Activity context, Dialog dialog){
        mContext = context;
        mDialog = dialog;
    }

    public ImageView getImageView(){
        // trigger the popup dialog
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // get dialog view
        View dialogView = mContext.getLayoutInflater().inflate(R.layout.dialog_preview, null);
        ImageView dialogImageView = (ImageView) dialogView.findViewById(R.id.image_view);

        // setup
        mDialog = alertDialog.setView(dialogView).create();

        // the other way to present dialog
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(mDialog.getWindow().getAttributes());

        // get width and make it square
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels;
        float dpHeight = displayMetrics.heightPixels;

        // check orientation
        if (mContext.getResources().getConfiguration().orientation ==
                mContext.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {

            // fixed edge
            layoutParams.width = (int) dpHeight - Constants.ORIENTATION_ADJUST;
            layoutParams.height = (int) dpHeight - Constants.ORIENTATION_ADJUST;

        } else {

            // fixed edge
            layoutParams.width = (int) dpWidth;
            layoutParams.height = (int) dpWidth;
        }

        mDialog.show();

        // show dialog with attributes
        mDialog.getWindow().setAttributes(layoutParams);
        // animation
        mDialog.getWindow().setWindowAnimations(R.style.DialogTheme);

        return dialogImageView;
    }

    public Dialog getDialog(){
        return mDialog;
    }
}
