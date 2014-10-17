package com.kirkbackus.drumpad;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnTouchListener {

    SoundPoolHelper soundPoolHelper;

    Integer hiHatSound, kickSound, snareSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPoolHelper = new SoundPoolHelper(3, this);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        hiHatSound = soundPoolHelper.load(this, R.raw.hi_hat_a, 1);
        kickSound = soundPoolHelper.load(this, R.raw.kick_a, 1);
        snareSound = soundPoolHelper.load(this, R.raw.snare_a, 1);

        Button snareDrumButton = (Button) findViewById(R.id.snareDrumButton);
        Button bassDrumButton = (Button)findViewById(R.id.bassDrumButton);
        Button hiHatButton = (Button)findViewById(R.id.hiHatButton);

        snareDrumButton.setOnTouchListener(this);
        bassDrumButton.setOnTouchListener(this);
        hiHatButton.setOnTouchListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPoolHelper.release();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            switch (view.getId()) {
                case R.id.bassDrumButton:
                    soundPoolHelper.play(kickSound);
                    break;
                case R.id.snareDrumButton:
                    soundPoolHelper.play(snareSound);
                    break;
                case R.id.hiHatButton:
                    soundPoolHelper.play(hiHatSound);
                    break;
                default:
                    break;
            }
        }

        return false;
    }
}
