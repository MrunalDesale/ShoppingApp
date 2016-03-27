package com.shoppingpad.view;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shoppingpad.R;
import com.shoppingpad.viewmodel.ContentViewModelHandler;

/**
 * Created by bridgelabz3 on 13/3/16.
 * Purpose:
 *
 * 1.Initial flow of program starts from this class
 * 2.This class display UI.
 * 3.It takes data from ViewModel and shows components to user.
 * 4.It contains components and methods required by user for interaction.
 */

public class ContentListView extends AppCompatActivity{

    RecyclerView mRecyclerView;
    ContentViewModelHandler mContentViewModelHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView= (RecyclerView) findViewById(R.id.recycleView);

        //Calls ViewModel constructor...
        mContentViewModelHandler=new ContentViewModelHandler(ContentListView.this);
        new DemoAsync().execute();
    }

    //Async task that populates data...
    class DemoAsync extends AsyncTask<String,String,String>{

        ProgressDialog progressDialog=new ProgressDialog(ContentListView.this);

        @Override
        protected String doInBackground(String... strings)
        {
            //Code to set contents in view...
            mContentViewModelHandler.populateData();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //Code to set adapter in View...
            progressDialog.dismiss();
            super.onPostExecute(s);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(ContentListView.this));
            mRecyclerView.setAdapter(new ContentListAdapter(mContentViewModelHandler));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Code to show processing dialog box with proper message...
            progressDialog.setMessage("Loading..!");
            progressDialog.setTitle("Please wait..");
            progressDialog.show();
        }
    }
}