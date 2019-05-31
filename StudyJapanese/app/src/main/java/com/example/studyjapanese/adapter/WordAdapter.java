package com.example.studyjapanese.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studyjapanese.R;
import com.example.studyjapanese.data.WordContract;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {
    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Context mContext;

    public WordAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the word_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.activity_word, parent, false);

        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {

        // Indices for the _id, word, and category columns
        int idIndex = mCursor.getColumnIndex(WordContract.WordEntry._ID);
        int japaneseWordIndex = mCursor.getColumnIndex(WordContract.WordEntry.COLUMN_WORD);
        int chineseMeaningIndex = mCursor.getColumnIndex(WordContract.WordEntry.COLUMN_MEAN);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String word = mCursor.getString(japaneseWordIndex);
        String mean = mCursor.getString(chineseMeaningIndex);
        //Set values
        holder.itemView.setTag(id);
        holder.japaneseWordView.setText(word);
        holder.chineseMeaningView.setText(mean);
    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }


    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }


    // Inner class for creating ViewHolders
    class WordViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        TextView japaneseWordView;
        TextView chineseMeaningView;
        //TextView categoryView;

        public WordViewHolder(View itemView) {
            super(itemView);
            japaneseWordView = (TextView) itemView.findViewById(R.id.editTextJapaneseWord);
            chineseMeaningView=(TextView) itemView.findViewById(R.id.chineseMeaning);
        }
    }
}
