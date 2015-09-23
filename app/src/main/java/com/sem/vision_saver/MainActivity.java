package com.sem.vision_saver;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends ActionBarActivity {

    private boolean isB = false;
    static String work = "работа";
    static String rest = "отдых";
    static Chronometer chrono;
    static TextView text1;
    private static final int NOTIFY_ID = 001;
    static ToggleButton togBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Context.startService(visionService);
        //запускаем сервис для уведомлений о отдыхе и работе

        chrono = (Chronometer) findViewById(R.id.chronometer);
        togBut = (ToggleButton) findViewById(R.id.toggleButton);
        //text1 = (TextView) findViewById(R.id.textView);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //final Button butStart = (Button) findViewById(R.id.button);



        //text1.setText(work);

        togBut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chrono.setBase(SystemClock.elapsedRealtime());
                    chrono.start();
                    //text1.setText(work);
                    startService(
                            new Intent(MainActivity.this, visionService.class));
                } else {
                    chrono.setBase(SystemClock.elapsedRealtime());
                    chrono.stop();
                    stopService(
                            new Intent(MainActivity.this, visionService.class));
                }
            }
        });

        /* chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            Context context = getApplicationContext();

            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long myElapsedTime = SystemClock.elapsedRealtime() - chrono.getBase();
                if (isB == false) {
                    if (myElapsedTime > 10000) {
                        text1.setText(rest);
                        chrono.setBase(SystemClock.elapsedRealtime());
                        isB = true;
                    }
                }
                else {
                    if (myElapsedTime > 5000) {
                        text1.setText(work);
                        chrono.setBase(SystemClock.elapsedRealtime());
                        isB = false;
                    }
                }
//1800000 30минут

            }
        }); */
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
