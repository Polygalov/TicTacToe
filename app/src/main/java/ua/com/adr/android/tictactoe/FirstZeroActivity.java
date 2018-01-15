package ua.com.adr.android.tictactoe;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class FirstZeroActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8;
    TextView tv1;
    int rndCell;
    Random rand;

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mCrossSound, mZeroSound, mDrawSound, mWinSound, mLoseSound;
    private int mStreamID;

    ArrayList<Integer> allX = new ArrayList<Integer>();
    ArrayList<Integer> all0 = new ArrayList<Integer>();
    ArrayList<Integer> allTotal = new ArrayList<Integer>();
    ArrayList<ImageView> allButton = new ArrayList<ImageView>();
    private int attemp;
    private int defance;
    //Объявляем использование анимации AnimationDrawable
    AnimationDrawable animation;
    Boolean isEasy;
    int id_level;
    DBHelper dbHelper;
    SQLiteDatabase db;
    final String SAVED_STATE = "saved_sound_state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isEasy = getIntent().getExtras().getBoolean("isEasy");
        rand = new Random();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.BLUE));
        actionBar.setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.app_name) + "</font>")));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn1 = (ImageView) findViewById(R.id.btn1);
        btn2 = (ImageView) findViewById(R.id.btn2);
        btn3 = (ImageView) findViewById(R.id.btn3);
        btn4 = (ImageView) findViewById(R.id.btn4);
        btn5 = (ImageView) findViewById(R.id.btn5);
        btn6 = (ImageView) findViewById(R.id.btn6);
        btn7 = (ImageView) findViewById(R.id.btn7);
        btn8 = (ImageView) findViewById(R.id.btn8);
        btn9 = (ImageView) findViewById(R.id.btn9);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        imageView5 = (ImageView) findViewById(R.id.imageView5);
        imageView6 = (ImageView) findViewById(R.id.imageView6);
        imageView7 = (ImageView) findViewById(R.id.imageView7);
        imageView8 = (ImageView) findViewById(R.id.imageView8);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setText("Xод соперника!");

        imageView1.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.INVISIBLE);
        imageView4.setVisibility(View.INVISIBLE);
        imageView5.setVisibility(View.INVISIBLE);
        imageView6.setVisibility(View.INVISIBLE);
        imageView7.setVisibility(View.INVISIBLE);
        imageView8.setVisibility(View.INVISIBLE);

        imageView1.bringToFront();
        imageView2.bringToFront();
        imageView3.bringToFront();
        imageView4.bringToFront();
        imageView5.bringToFront();
        imageView6.bringToFront();
        imageView7.bringToFront();
        imageView8.bringToFront();

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);

        allButton.add(btn1);
        allButton.add(btn2);
        allButton.add(btn3);
        allButton.add(btn4);
        allButton.add(btn5);
        allButton.add(btn6);
        allButton.add(btn7);
        allButton.add(btn8);
        allButton.add(btn9);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        if (isEasy) {
            id_level = 3;
        } else
            id_level = 4;

        checkWin();

    }


    public void setCross(ImageView iv) {
        iv.setBackgroundResource(R.drawable.anim_button_cross);
        animation = (AnimationDrawable) iv.getBackground();
        animation.setOneShot(true);
        animation.stop();
        animation.start();
    }

    public void setZero(ImageView iv) {
        iv.setBackgroundResource(R.drawable.anim_button_zero);
        animation = (AnimationDrawable) iv.getBackground();
        animation.setOneShot(true);
        animation.stop();
        animation.start();
    }

    @Override
    public void onClick(View v) {
        // по id определеяем кнопку, вызвавшую этот обработчик
        switch (v.getId()) {
            case R.id.btn1:
                setCross(btn1);
                btn1.setEnabled(false);
                allX.add(1);
                allTotal.add(1);
                checkWin();
                playSound(mCrossSound);
                break;
            case R.id.btn2:
                setCross(btn2);
                btn2.setEnabled(false);
                allX.add(2);
                allTotal.add(2);
                playSound(mCrossSound);
                checkWin();
                break;
            case R.id.btn3:
                setCross(btn3);
                btn3.setEnabled(false);
                allX.add(3);
                allTotal.add(3);
                playSound(mCrossSound);
                checkWin();
                break;
            case R.id.btn4:
                setCross(btn4);
                btn4.setEnabled(false);
                allX.add(4);
                allTotal.add(4);
                playSound(mCrossSound);
                checkWin();
                break;
            case R.id.btn5:
                setCross(btn5);
                btn5.setEnabled(false);
                allX.add(5);
                allTotal.add(5);
                playSound(mCrossSound);
                checkWin();
                break;
            case R.id.btn6:
                setCross(btn6);
                btn6.setEnabled(false);
                allX.add(6);
                allTotal.add(6);
                playSound(mCrossSound);
                checkWin();
                break;
            case R.id.btn7:
                setCross(btn7);
                btn7.setEnabled(false);
                allX.add(7);
                allTotal.add(7);
                playSound(mCrossSound);
                checkWin();
                break;
            case R.id.btn8:
                setCross(btn8);
                btn8.setEnabled(false);
                allX.add(8);
                allTotal.add(8);
                playSound(mCrossSound);
                checkWin();
                break;
            case R.id.btn9:
                setCross(btn9);
                btn9.setEnabled(false);
                allX.add(9);
                allTotal.add(9);
                playSound(mCrossSound);
                checkWin();
                break;
        }
    }

    private void checkWin() {
        if (allX.contains(1) && allX.contains(2) && allX.contains(3)) {
            setCustomVisibility(imageView1);
            runWin();
        } else if (allX.contains(4) && allX.contains(5) && allX.contains(6)) {
            setCustomVisibility(imageView2);
            runWin();
        } else if (allX.contains(7) && allX.contains(8) && allX.contains(9)) {
            setCustomVisibility(imageView3);
            runWin();
        } else if (allX.contains(1) && allX.contains(4) && allX.contains(7)) {
            setCustomVisibility(imageView4);
            runWin();
        } else if (allX.contains(2) && allX.contains(5) && allX.contains(8)) {
            setCustomVisibility(imageView5);
            runWin();
        } else if (allX.contains(3) && allX.contains(6) && allX.contains(9)) {
            setCustomVisibility(imageView6);
            runWin();
        } else if (allX.contains(7) && allX.contains(5) && allX.contains(3)) {
            setCustomVisibility(imageView8);
            runWin();
        } else if (allX.contains(1) && allX.contains(5) && allX.contains(9)) {
            setCustomVisibility(imageView7);
            runWin();

        } else {
            tv1.setText("Xод соперника!");
            allButtonsDisable();
            if (sumArrayList() == 0) { //если ход первый, то с короткой задержкой
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rndCell = logicAndroid();
                        playSound(mZeroSound);
                        setZeroHod(rndCell);
                    }
                }, 500);
            } else {

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (sumArrayList() < 45) {
                            rndCell = logicAndroid();
                            setZeroHod(rndCell);
                        } else {
                            draw();
                        }
                    }
                }, 1500);
            }
        }
    }

    public void runWin() {
        playSound(mWinSound);
        int winTableScore = dbHelper.getValue("WIN", id_level) + 1;
        if (id_level == 3) {
            dbHelper.updateTable(db, "Easy_0", 3, winTableScore, dbHelper.getValue("LOSE", id_level), dbHelper.getValue("DRAW", id_level));
        } else {
            dbHelper.updateTable(db, "Hard_0", 4, winTableScore, dbHelper.getValue("LOSE", id_level), dbHelper.getValue("DRAW", id_level));
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                youWin();
            }
        }, 1500);
    }

    private int logicAndroid() {
        int res = 0;
        if (isWinCombination()) {
            return attemp;
        } else if (!isEasy && isChanceToLose()) {
            return defance;
        } else {
            if (!allTotal.contains(5)) {
                res = 5;
            } else {
                if (all0.size() == 0) {
                    if (!allTotal.contains(1)) {
                        res = 1;
                    } else if (!allTotal.contains(3)) {
                        res = 3;
                    }
                } else {
                    res = secondHod();
                }
            }
        }
        return res;
    }

    private int sumArrayList() {
        int sum = 0;

        for (int index : allTotal) {
            sum += index;
        }
        return sum;
    }

    private void checkWinAndroid() {
        if (all0.contains(1) && all0.contains(2) && all0.contains(3)) {
            setCustomVisibility(imageView1);
            runLoose();
        } else if (all0.contains(4) && all0.contains(5) && all0.contains(6)) {
            setCustomVisibility(imageView2);
            runLoose();
        } else if (all0.contains(7) && all0.contains(8) && all0.contains(9)) {
            setCustomVisibility(imageView3);
            runLoose();
        } else if (all0.contains(1) && all0.contains(4) && all0.contains(7)) {
            setCustomVisibility(imageView4);
            runLoose();
        } else if (all0.contains(2) && all0.contains(5) && all0.contains(8)) {
            setCustomVisibility(imageView5);
            runLoose();
        } else if (all0.contains(3) && all0.contains(6) && all0.contains(9)) {
            setCustomVisibility(imageView6);
            runLoose();
        } else if (all0.contains(7) && all0.contains(5) && all0.contains(3)) {
            setCustomVisibility(imageView8);
            runLoose();
        } else if (all0.contains(1) && all0.contains(5) && all0.contains(9)) {
            setCustomVisibility(imageView7);
            runLoose();

        } else {
            if (sumArrayList() == 45) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        draw();
                    }
                }, 1500);

            } else {
                tv1.setText("Ваш ход!");
                buttonsEnable();
            }
        }
    }

    public void runLoose() {
        playSound(mLoseSound);
        int loseTableScore = dbHelper.getValue("LOSE", id_level) + 1;
        if (id_level == 3) {
            dbHelper.updateTable(db, "Easy_0", 3, dbHelper.getValue("WIN", id_level), loseTableScore, dbHelper.getValue("DRAW", id_level));
        } else {
            dbHelper.updateTable(db, "Hard_0", 4, dbHelper.getValue("WIN", id_level), loseTableScore, dbHelper.getValue("DRAW", id_level));
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                youLoose();
            }
        }, 1500);
    }

    public void setCustomVisibility(ImageView iv) {
        final ImageView imVi = iv;
        new CountDownTimer(2000, 300) {
            int count = 0;

            public void onTick(long millisUntilFinished) {
                if (count % 2 == 0) {
                    imVi.setVisibility(View.INVISIBLE);
                } else
                    imVi.setVisibility(View.VISIBLE);
                count++;
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }


    private void setZeroHod(int CellNumber) {
        switch (CellNumber) {
            case 1:
                setZero(btn1);
                btn1.setEnabled(false);
                all0.add(1);
                allTotal.add(1);
                playSound(mZeroSound);
                checkWinAndroid();
                break;
            case 2:
                setZero(btn2);
                btn2.setEnabled(false);
                all0.add(2);
                allTotal.add(2);
                playSound(mZeroSound);
                checkWinAndroid();
                break;
            case 3:
                setZero(btn3);
                btn3.setEnabled(false);
                all0.add(3);
                allTotal.add(3);
                playSound(mZeroSound);
                checkWinAndroid();
                break;
            case 4:
                setZero(btn4);
                btn4.setEnabled(false);
                all0.add(4);
                allTotal.add(4);
                playSound(mZeroSound);
                checkWinAndroid();
                break;
            case 5:
                setZero(btn5);
                btn5.setEnabled(false);
                all0.add(5);
                allTotal.add(5);
                playSound(mZeroSound);
                checkWinAndroid();
                break;
            case 6:
                setZero(btn6);
                btn6.setEnabled(false);
                all0.add(6);
                allTotal.add(6);
                playSound(mZeroSound);
                checkWinAndroid();
                break;
            case 7:
                setZero(btn7);
                btn7.setEnabled(false);
                all0.add(7);
                allTotal.add(7);
                playSound(mZeroSound);
                checkWinAndroid();
                break;
            case 8:
                setZero(btn8);
                btn8.setEnabled(false);
                all0.add(8);
                allTotal.add(8);
                playSound(mZeroSound);
                checkWinAndroid();
                break;
            case 9:
                setZero(btn9);
                btn9.setEnabled(false);
                all0.add(9);
                allTotal.add(9);
                playSound(mZeroSound);
                checkWinAndroid();
                break;
        }
    }

    private void allButtonsDisable() {
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        btn5.setEnabled(false);
        btn6.setEnabled(false);
        btn7.setEnabled(false);
        btn8.setEnabled(false);
        btn9.setEnabled(false);
    }

    private void buttonsEnable() {
        for (ImageView button : allButton) {
            button.setEnabled(true);
        }
        for (int indexCell : allTotal) {
            allButton.get(indexCell - 1).setEnabled(false);
        }
    }

    public void restartActivity(Activity act) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public boolean isWinCombination() {
        boolean isWinComb = false;
        if (all0.contains(1) && all0.contains(2) && !allX.contains(3)) {
            attemp = 3;
            isWinComb = true;
        } else if (all0.contains(2) && all0.contains(3) && !allX.contains(1)) {
            attemp = 1;
            isWinComb = true;
        } else if (all0.contains(1) && all0.contains(3) && !allX.contains(2)) {
            attemp = 2;
            isWinComb = true;
        } else if (all0.contains(4) && all0.contains(5) && !allX.contains(6)) {
            attemp = 6;
            isWinComb = true;
        } else if (all0.contains(5) && all0.contains(6) && !allX.contains(4)) {
            attemp = 4;
            isWinComb = true;
        } else if (all0.contains(4) && all0.contains(6) && !allX.contains(5)) {
            attemp = 5;
            isWinComb = true;
        } else if (all0.contains(7) && all0.contains(8) && !allX.contains(9)) {
            attemp = 9;
            isWinComb = true;
        } else if (all0.contains(8) && all0.contains(9) && !allX.contains(7)) {
            attemp = 7;
            isWinComb = true;
        } else if (all0.contains(7) && all0.contains(9) && !allX.contains(8)) {
            attemp = 8;
            isWinComb = true;
        } else if (all0.contains(1) && all0.contains(4) && !allX.contains(7)) {
            attemp = 7;
            isWinComb = true;
        } else if (all0.contains(4) && all0.contains(7) && !allX.contains(1)) {
            attemp = 1;
            isWinComb = true;
        } else if (all0.contains(1) && all0.contains(7) && !allX.contains(4)) {
            attemp = 4;
            isWinComb = true;
        } else if (all0.contains(2) && all0.contains(5) && !allX.contains(8)) {
            attemp = 8;
            isWinComb = true;
        } else if (all0.contains(2) && all0.contains(8) && !allX.contains(5)) {
            attemp = 5;
            isWinComb = true;
        } else if (all0.contains(5) && all0.contains(8) && !allX.contains(2)) {
            attemp = 2;
            isWinComb = true;
        } else if (all0.contains(3) && all0.contains(6) && !allX.contains(9)) {
            attemp = 9;
            isWinComb = true;
        } else if (all0.contains(6) && all0.contains(9) && !allX.contains(3)) {
            attemp = 3;
            isWinComb = true;
        } else if (all0.contains(3) && all0.contains(9) && !allX.contains(6)) {
            attemp = 6;
            isWinComb = true;
        } else if (all0.contains(7) && all0.contains(5) && !allX.contains(3)) {
            attemp = 3;
            isWinComb = true;
        } else if (all0.contains(5) && all0.contains(3) && !allX.contains(7)) {
            attemp = 7;
            isWinComb = true;
        } else if (all0.contains(7) && all0.contains(3) && !allX.contains(5)) {
            attemp = 5;
            isWinComb = true;
        } else if (all0.contains(1) && all0.contains(5) && !allX.contains(9)) {
            attemp = 9;
            isWinComb = true;
        } else if (all0.contains(1) && all0.contains(9) && !allX.contains(5)) {
            attemp = 5;
            isWinComb = true;
        } else if (all0.contains(1) && all0.contains(9) && !allX.contains(5)) {
            attemp = 5;
            isWinComb = true;
        }
        return isWinComb;
    }

    public boolean isChanceToLose() {
        boolean isLoseChance = false;
        if (allX.contains(1) && allX.contains(2) && !all0.contains(3)) {
            defance = 3;
            isLoseChance = true;
        } else if (allX.contains(2) && allX.contains(3) && !all0.contains(1)) {
            defance = 1;
            isLoseChance = true;
        } else if (allX.contains(1) && allX.contains(3) && !all0.contains(2)) {
            defance = 2;
            isLoseChance = true;
        } else if (allX.contains(4) && allX.contains(5) && !all0.contains(6)) {
            defance = 6;
            isLoseChance = true;
        } else if (allX.contains(5) && allX.contains(6) && !all0.contains(4)) {
            defance = 4;
            isLoseChance = true;
        } else if (allX.contains(4) && allX.contains(6) && !all0.contains(5)) {
            defance = 5;
            isLoseChance = true;
        } else if (allX.contains(7) && allX.contains(8) && !all0.contains(9)) {
            defance = 9;
            isLoseChance = true;
        } else if (allX.contains(8) && allX.contains(9) && !all0.contains(7)) {
            defance = 7;
            isLoseChance = true;
        } else if (allX.contains(7) && allX.contains(9) && !all0.contains(8)) {
            defance = 8;
            isLoseChance = true;
        } else if (allX.contains(1) && allX.contains(4) && !all0.contains(7)) {
            defance = 7;
            isLoseChance = true;
        } else if (allX.contains(4) && allX.contains(7) && !all0.contains(1)) {
            defance = 1;
            isLoseChance = true;
        } else if (allX.contains(1) && allX.contains(7) && !all0.contains(4)) {
            defance = 4;
            isLoseChance = true;
        } else if (allX.contains(2) && allX.contains(5) && !all0.contains(8)) {
            defance = 8;
            isLoseChance = true;
        } else if (allX.contains(2) && allX.contains(8) && !all0.contains(5)) {
            defance = 5;
            isLoseChance = true;
        } else if (allX.contains(5) && allX.contains(8) && !all0.contains(2)) {
            defance = 2;
            isLoseChance = true;
        } else if (allX.contains(3) && allX.contains(6) && !all0.contains(9)) {
            defance = 9;
            isLoseChance = true;
        } else if (allX.contains(6) && allX.contains(9) && !all0.contains(3)) {
            defance = 3;
            isLoseChance = true;
        } else if (allX.contains(3) && allX.contains(9) && !all0.contains(6)) {
            defance = 6;
            isLoseChance = true;
        } else if (allX.contains(7) && allX.contains(5) && !all0.contains(3)) {
            defance = 3;
            isLoseChance = true;
        } else if (allX.contains(5) && allX.contains(3) && !all0.contains(7)) {
            defance = 7;
            isLoseChance = true;
        } else if (allX.contains(7) && allX.contains(3) && !all0.contains(5)) {
            defance = 5;
            isLoseChance = true;
        } else if (allX.contains(1) && allX.contains(5) && !all0.contains(9)) {
            defance = 9;
            isLoseChance = true;
        } else if (allX.contains(1) && allX.contains(9) && !all0.contains(5)) {
            defance = 5;
            isLoseChance = true;
        } else if (allX.contains(1) && allX.contains(9) && !all0.contains(5)) {
            defance = 5;
            isLoseChance = true;
        }
        return isLoseChance;
    }

    private int secondHod() {
        int secondChoice = 0;
        if (allX.contains(6) && allX.contains(7) && !allX.contains(9) && !all0.contains(9)) {
            secondChoice = 9;
        } else if (allX.contains(4) && allX.contains(9) && !allX.contains(7) && !all0.contains(7)) {
            secondChoice = 7;
        } else if (all0.contains(1) && !allX.contains(4) && !allX.contains(7)) {
            secondChoice = 7;
        } else if (all0.contains(1) && !allX.contains(2) && !allX.contains(3)) {
            secondChoice = 3;
        } else if (all0.contains(1) && !allX.contains(5) && !allX.contains(9)) {
            secondChoice = 5;
        } else if (all0.contains(3) && !allX.contains(2) && !allX.contains(1)) {
            secondChoice = 1;
        } else if (all0.contains(3) && !allX.contains(6) && !allX.contains(9)) {
            secondChoice = 9;
        } else if (all0.contains(3) && !allX.contains(5) && !allX.contains(7)) {
            secondChoice = 5;
        } else if (all0.contains(5) && !allX.contains(7) && !allX.contains(3)) {
            secondChoice = 3;
        } else if (all0.contains(5) && !allX.contains(1) && !allX.contains(9)) {
            secondChoice = 1;
        } else if (all0.contains(1) && !allX.contains(5) && !allX.contains(9)) {
            secondChoice = 5;
        } else if (all0.contains(7) && !allX.contains(8) && !allX.contains(9)) {
            secondChoice = 9;
        } else if (all0.contains(9) && !allX.contains(5) && !allX.contains(7)) {
            secondChoice = 7;
        } else {
            do {
                secondChoice = rand.nextInt(9) + 1;
            } while (allTotal.contains(secondChoice));
        }
        return secondChoice;
    }

    public void youWin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FirstZeroActivity.this);
        builder.setTitle("Поздравляем!")
                .setMessage("Вы выиграли!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Начать заново",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                restartActivity(FirstZeroActivity.this);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Выйти",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finishAffinity();
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void draw() {
        playSound(mDrawSound);
        int drawTableScore = dbHelper.getValue("DRAW", id_level) + 1;
        if (id_level == 3) {
            dbHelper.updateTable(db, "Easy_0", 3, dbHelper.getValue("WIN", id_level), dbHelper.getValue("LOSE", id_level), drawTableScore);
        } else {
            dbHelper.updateTable(db, "Hard_0", 4, dbHelper.getValue("WIN", id_level), dbHelper.getValue("LOSE", id_level), drawTableScore);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(FirstZeroActivity.this);
        builder.setTitle("Ничья!")
                .setMessage("Ничья!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Начать заново",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                restartActivity(FirstZeroActivity.this);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Выйти",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finishAffinity();
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void youLoose() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FirstZeroActivity.this);
        builder.setTitle("Соболезнуем!")
                .setMessage("Вы проиграли!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Начать заново",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                restartActivity(FirstZeroActivity.this);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Выйти",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finishAffinity();
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0 && GlobalVars.isSounds) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        mCrossSound = loadSound("cross.mp3");
        mZeroSound = loadSound("zero.mp3");
        mDrawSound = loadSound("draw.mp3");
        mWinSound = loadSound("win.mp3");
        mLoseSound = loadSound("lose.mp3");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (GlobalVars.isSounds) {
            menu.findItem(R.id.action_sounds).setChecked(true);
        } else
            menu.findItem(R.id.action_sounds).setChecked(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_statistic:
                StatisticFragment myDialogFragment = new StatisticFragment();
                FragmentManager manager = getSupportFragmentManager();
                myDialogFragment.show(manager, "dialog");
                return true;
            case R.id.action_sounds:
                if (item.isChecked()) {
                    // If item already checked then unchecked it
                    item.setChecked(false);
                    GlobalVars.isSounds = false;
                } else {
                    // If item is unchecked then checked it
                    item.setChecked(true);
                    GlobalVars.isSounds = true;
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SavePreferences();
    }

    public void SavePreferences() {
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean(SAVED_STATE, GlobalVars.isSounds);
        editor.apply();
    }
}
