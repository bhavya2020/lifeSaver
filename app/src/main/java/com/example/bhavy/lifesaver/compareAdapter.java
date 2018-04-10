package com.example.bhavy.lifesaver;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhavy.lifesaver.data.waterContract;

/**
 * Created by bhavy on 12-08-2017.
 */

public class compareAdapter extends RecyclerView.Adapter<compareAdapter.compareHolder> {

    // Holds on to the cursor to display the waitlist
    private Cursor mCursor;
    private Context mContext;

    /**
     * Constructor using the context and the db cursor
     * @param context the calling context/activity
     * @param cursor the db cursor with waitlist data to display
     */
    public compareAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public compareHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.compare_item, parent, false);
        return new compareHolder(view);
    }

    @Override
    public void onBindViewHolder(compareHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null
        int count = mCursor.getInt(mCursor.getColumnIndex(waterContract.WaterDay.COUNT));
        String time = mCursor.getString(mCursor.getColumnIndex(waterContract.WaterDay.COLUMN_TIMESTAMP));
        long id = mCursor.getLong(mCursor.getColumnIndex(waterContract.WaterDay.ID));
        holder.countTextView.setText(String.valueOf((float)count/2));
        holder.itemView.setTag(id);
        holder.time.setText(String.valueOf(time));
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    /**
     * Swaps the Cursor currently held in the adapter with a new one
     * and triggers a UI refresh
     *
     * @param newCursor the new cursor that will replace the existing one
     */
    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class compareHolder extends RecyclerView.ViewHolder {



        TextView countTextView;
        TextView time;
        public compareHolder(View itemView) {
            super(itemView);
           countTextView = (TextView) itemView.findViewById(R.id.count_text_view);
            time= (TextView) itemView.findViewById(R.id.time);

        }

    }
}
