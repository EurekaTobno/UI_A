package com.example.tobno.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.tobno.helloworld.recycler.SimpleAdapter;
import com.example.tobno.helloworld.recycler.SlideInOutRightItemAnimator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tobno on 2017/8/6.
 */

public class DemoActivity extends AppCompatActivity {
    private static final int[] TIME_SPAN = {2000, 3000, 5000, 8000, 10000};
    private int timeSpan = TIME_SPAN[0];
    RecyclerView mRecyclerView;
    ImageView clearView;
    SimpleAdapter mAdapter;
    Timer timer;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        setupSpinner();

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mAdapter = new SimpleAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new SlideInOutRightItemAnimator(mRecyclerView, DemoActivity.this));

        timer = new Timer();
        timer.schedule(new MyTimerTask(), 500, timeSpan);


        clearView = (ImageView) findViewById(R.id.clear);
        clearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.clear();

                timer.cancel();
                timer = new Timer();
                timer.schedule(new MyTimerTask(), 500, timeSpan);
            }
        });
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            handler.post(runnableUi);
        }

    }

    Runnable runnableUi = new Runnable() {
        @Override
        public void run() {
            mAdapter.add(SimpleAdapter.LAST_POSITION);
            int itemCount = mAdapter.getItemCount();
            if (itemCount >= 4) {
                timer.cancel();
            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private void setupSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_span, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != -1 && position < TIME_SPAN.length) {
                    timeSpan = TIME_SPAN[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
