package com.example.studyjapanese;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.studyjapanese.adapter.WordAdapter;
import com.example.studyjapanese.data.WordContract;

import java.util.ArrayList;
import java.util.List;

public class WordActivity extends AppCompatActivity  {
    RecyclerView mRecyclerView;
    private WordAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_word);
        mRecyclerView = (RecyclerView) findViewById(R.id.word_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new WordAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                int id = (int) viewHolder.itemView.getTag();
                String stringId = Integer.toString(id);
                Uri uri = WordContract.WordEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();
                getContentResolver().delete(uri, null, null);
                mAdapter.swapCursor(getAllEntries());
            }
        }).attachToRecyclerView(mRecyclerView);

        FloatingActionButton test = findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addWordIntent = new Intent(WordActivity.this, AddWordActivity.class);
                startActivity(addWordIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // re-queries for all tasks
    }

    private Cursor getAllEntries(){
        Cursor mCusor;
        String selection = null;
        String[] selectionArgs = {""};
        mCusor=getContentResolver().query(WordContract.WordEntry.CONTENT_URI,null,selection,selectionArgs,null);
        return mCusor;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_word) {
            Intent addWordIntent = new Intent(WordActivity.this, AddWordActivity.class);
            startActivity(addWordIntent);
            return true;
        }
        if (id == R.id.setting) {
            Context context = WordActivity.this;
            String textToShow = "Setting";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.sorting) {
            Context context = WordActivity.this;
            String textToShow = "Sorting";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
