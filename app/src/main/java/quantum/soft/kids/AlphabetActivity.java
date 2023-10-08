package quantum.soft.kids;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


import quantum.soft.kids.utilworks.BackgroundSoundService;

public class AlphabetActivity extends Activity implements OnClickListener, OnTouchListener {

    ImageView nextBtn = null;
    ImageView playBtn = null;
    ImageView prevBtn = null;
    ImageView itemCapital = null;
    private int currentPosition = 0;
    private int totalItem = 0;
    private MediaPlayer mp = null;
    ResourcePool resourcePool = new ResourcePool();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.taw_activity_alphabet);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }



        Intent svc = new Intent(this, BackgroundSoundService.class);
        svc.putExtra("isPlay", false);
        startService(svc);



        nextBtn = (ImageView) findViewById(R.id.nextId);
        playBtn = (ImageView) findViewById(R.id.playId);
        prevBtn = (ImageView) findViewById(R.id.prevId);
        nextBtn.setOnTouchListener(this);
        playBtn.setOnTouchListener(this);
        prevBtn.setOnTouchListener(this);

        itemCapital = (ImageView) findViewById(R.id.capitalItemId);

        nextBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);
        itemCapital.setOnClickListener(this);

        totalItem = resourcePool.alphabetCapital.length;
        itemCapital.setImageResource(resourcePool.alphabetCapital[currentPosition]);

        updateNextButton();
        mp = MediaPlayer.create(AlphabetActivity.this, resourcePool.alphabetSound[currentPosition]);
        mp.start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tawmain, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextId:
                interad();
                gotoNext();
                break;
            case R.id.prevId:
                interad();
                gotoPrevious();
                break;
            case R.id.playId:
                playSound();
                break;
            case R.id.capitalItemId:
                mp = MediaPlayer.create(AlphabetActivity.this, resourcePool.alphabetSound[currentPosition]);
                mp.start();
                break;

            default:
                break;
        }
    }

    private void gotoNext() {
        currentPosition++;
        updateNextButton();
        updatePreviousButton();
        if (currentPosition >= 0 && currentPosition < totalItem) {
            itemCapital.setImageResource(resourcePool.alphabetCapital[currentPosition]);
            mp = MediaPlayer.create(AlphabetActivity.this, resourcePool.alphabetSound[currentPosition]);
            mp.start();
        }
    }

    private void gotoPrevious() {
        currentPosition--;
        updateNextButton();
        updatePreviousButton();
        if (currentPosition >= 0 && currentPosition < totalItem) {
            itemCapital.setImageResource(resourcePool.alphabetCapital[currentPosition]);
            mp = MediaPlayer.create(AlphabetActivity.this, resourcePool.alphabetSound[currentPosition]);
            mp.start();
        }
    }

    private void updateNextButton() {
        if (currentPosition == totalItem - 1) {
            nextBtn.setAlpha(0.5f);
            nextBtn.setClickable(false);
        } else {
            nextBtn.setAlpha(1f);
            nextBtn.setClickable(true);
        }
    }

    private void updatePreviousButton() {
        if (currentPosition == 0) {
            prevBtn.setAlpha(0.5f);
            prevBtn.setClickable(false);
        } else {
            prevBtn.setAlpha(1f);
            prevBtn.setClickable(true);
        }
    }

    private void playSound() {
        mp = MediaPlayer.create(AlphabetActivity.this, resourcePool.alphabetSound[currentPosition]);
        mp.start();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setAlpha(0.5f);
                break;
            case MotionEvent.ACTION_UP:
                v.setAlpha(1.0f);
                break;

            default:
                break;
        }
        return false;
    }

    private void interad() {

        SharedPreferences prefss = getSharedPreferences("counter123", MODE_PRIVATE);
        final String count = prefss.getString("count", "");

        SharedPreferences prefssss = getApplicationContext().getSharedPreferences("counter123", 0);
        SharedPreferences.Editor editor = prefssss.edit();
        editor.putString("count", count + 1);
        Log.e("zzzz1", "onPageSelected: " + count);
        editor.commit();

        if (count.equals("1111")) {

            prefssss.edit().clear().commit();


        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AlphabetActivity.this, MainActivity.class);
        startActivity(intent);
    }



    private void load1() {

    }


    /**
     * Called when returning to the activity
     */


    /**
     * Called before the activity is destroyed
     */






}
