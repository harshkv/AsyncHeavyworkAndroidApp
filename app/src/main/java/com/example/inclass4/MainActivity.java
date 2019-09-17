package com.example.inclass4;

/*
TEAM 32
Sahil Sood
Harshitha Keshavaraju Vijayalakshmi
 */

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    TextView tv_minDisplay, tv_maxDisplay, tv_avgDisplay, tv_time;
    SeekBar seekbar;
    Button generateButton;
    int finalProgress;
    ProgressBar progressBar;
    ArrayList<Double> result = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_minDisplay = (TextView) findViewById(R.id.tv_minDisplay);
        tv_maxDisplay = (TextView) findViewById(R.id.tv_maxDisplay);
        tv_avgDisplay = (TextView) findViewById(R.id.tv_avgDisplay);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        generateButton = (Button) findViewById(R.id.generateButton);
        seekbar.setMax(10);


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setProgress(progress);
                tv_time.setText(progress + " Times");
                finalProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (finalProgress == 0) {
                    Toast.makeText(MainActivity.this, "Please select progress", Toast.LENGTH_SHORT).show();
                } else {
                    new DoworkAsync().execute(finalProgress);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    class DoworkAsync extends AsyncTask<Integer, Integer, ArrayList<Double>> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Double> aDouble) {
            progressBar.setVisibility(View.INVISIBLE);
            Log.i("demo", "sum onPostExecute" + aDouble.get(0).toString());
            tv_avgDisplay.setText(aDouble.get(0).toString());
            tv_minDisplay.setText(aDouble.get(1).toString());
            tv_maxDisplay.setText(aDouble.get(2).toString());

        }

        @Override
        protected ArrayList<Double> doInBackground(Integer... integers) {
            HeavyWork work = new HeavyWork();
            ArrayList<Double> calcArray = new ArrayList<>();

            Log.i("demo", "i came hrere" + integers[0]);
            calcArray = work.getArrayNumbers(integers[0]);
            Log.i("demo", "arrarylIST" + calcArray.toString());
            Double sum = 0.0, count, mins = 0.0, maxs = 0.0;
            Collections.sort(calcArray);
            mins = calcArray.get(0);
            maxs = calcArray.get(calcArray.size() - 1);

            for (Double x :
                    calcArray) {

                sum += x;
            }

            Double avg = sum / calcArray.size();
            Log.i("demo", "sum" + sum);
            Log.i("demo", "sum" + avg);

            result.add(0, avg);
            result.add(1, mins);
            result.add(2, maxs);

            return result;

        }


    }
}