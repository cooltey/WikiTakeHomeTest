package org.cooltey.wikicodingassignment.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.cooltey.wikicodingassignment.MainActivity;
import org.cooltey.wikicodingassignment.R;

import static android.app.Activity.RESULT_OK;


public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {

    private Cursor mData;
    private Activity mContext;
    private DatabaseHelper mDatabase;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View cardView;
        public ViewHolder(View v) {
            super(v);
            cardView = v;
        }
    }

    public SearchHistoryAdapter(Activity context, Cursor data) {
        mContext = context;
        mData = data;
        mDatabase = new DatabaseHelper(mContext);
    }

    @Override
    public SearchHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if(mData.moveToPosition(position)) {

            // setup views
            TextView historyView = (TextView) holder.cardView.findViewById(R.id.history_text);
            ImageView deleteView = (ImageView) holder.cardView.findViewById(R.id.delete_icon);

            // get values from db
            final long getId = mData.getLong(mData.getColumnIndexOrThrow("_id"));
            final String getSearchText = mData.getString(mData.getColumnIndexOrThrow("keyword"));

            historyView.setText(getSearchText);

            deleteView.setTag(getId);

            deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    // show dialog
                    AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(mContext);

                    mAlertDialog.setMessage(mContext.getString(R.string.dialog_msg));
                    mAlertDialog.setPositiveButton(mContext.getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // remove
                            long id = (long) view.getTag();
                            mDatabase.deleteKeyword(id);
                            mData = mDatabase.getKeywords();
                            notifyDataSetChanged();
                        }
                    });
                    mAlertDialog.setNegativeButton(mContext.getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    mAlertDialog.setCancelable(false);
                    mAlertDialog.show();


                }
            });

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.SEARCH_KEYWORD, getSearchText);

                    // go back
                    mContext.setResult(RESULT_OK, intent);
                    mContext.finish();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mData.getCount();
    }


}
