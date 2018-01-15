package ua.com.adr.android.tictactoe;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TwoPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8;
    TextView tv1;
    Random rand;
    boolean isHodCross = true;

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mCrossSound, mZeroSound, mDrawSound, mWinSound, mLoseSound;
    private int mStreamID;

    ArrayList<Integer> allX = new ArrayList<Integer>();
    ArrayList<Integer> all0 = new ArrayList<Integer>();
    ArrayList<Integer> allTotal = new ArrayList<Integer>();
    ArrayList<ImageView> allButton = new ArrayList<ImageView>();
    //Объявляем использование анимации AnimationDrawable
    AnimationDrawable animation;
    final String SAVED_STATE = "saved_sound_state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        tv1.setText("Ход Х!");

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
        switch (v.getId()) {
            case R.id.btn1:
                if (isHodCross) {
                    setCross(btn1);
                    btn1.setEnabled(false);
                    allX.add(1);
                    allTotal.add(1);
                    checkWin();
                    playSound(mCrossSound);
                    isHodCross = false;
                } else {
                    setZero(btn1);
                    btn1.setEnabled(false);
                    all0.add(1);
                    allTotal.add(1);
                    checkWinZero();
                    playSound(mZeroSound);
                    isHodCross = true;
                }
                break;
            case R.id.btn2:
                if (isHodCross) {
                    setCross(btn2);
                    btn2.setEnabled(false);
                    allX.add(2);
                    allTotal.add(2);
                    checkWin();
                    playSound(mCrossSound);
                    isHodCross = false;
                } else {
                    setZero(btn2);
                    btn2.setEnabled(false);
                    all0.add(2);
                    allTotal.add(2);
                    checkWinZero();
                    playSound(mZeroSound);
                    isHodCross = true;
                }
                break;
            case R.id.btn3:
                if (isHodCross) {
                    setCross(btn3);
                    btn3.setEnabled(false);
                    allX.add(3);
                    allTotal.add(3);
                    checkWin();
                    playSound(mCrossSound);
                    isHodCross = false;
                } else {
                    setZero(btn3);
                    btn3.setEnabled(false);
                    all0.add(3);
                    allTotal.add(3);
                    checkWinZero();
                    playSound(mZeroSound);
                    isHodCross = true;
                }
                break;
            case R.id.btn4:
                if (isHodCross) {
                    setCross(btn4);
                    btn4.setEnabled(false);
                    allX.add(4);
                    allTotal.add(4);
                    checkWin();
                    playSound(mCrossSound);
                    isHodCross = false;
                } else {
                    setZero(btn4);
                    btn4.setEnabled(false);
                    all0.add(4);
                    allTotal.add(4);
                    checkWinZero();
                    playSound(mZeroSound);
                    isHodCross = true;
                }
                break;
            case R.id.btn5:
                if (isHodCross) {
                    setCross(btn5);
                    btn5.setEnabled(false);
                    allX.add(5);
                    allTotal.add(5);
                    checkWin();
                    playSound(mCrossSound);
                    isHodCross = false;
                } else {
                    setZero(btn5);
                    btn5.setEnabled(false);
                    all0.add(5);
                    allTotal.add(5);
                    checkWinZero();
                    playSound(mZeroSound);
                    isHodCross = true;
                }
                break;
            case R.id.btn6:
                if (isHodCross) {
                    setCross(btn6);
                    btn6.setEnabled(false);
                    allX.add(6);
                    allTotal.add(6);
                    checkWin();
                    playSound(mCrossSound);
                    isHodCross = false;
                } else {
                    setZero(btn6);
                    btn6.setEnabled(false);
                    all0.add(6);
                    allTotal.add(6);
                    checkWinZero();
                    playSound(mZeroSound);
                    isHodCross = true;
                }
                break;
            case R.id.btn7:
                if (isHodCross) {
                    setCross(btn7);
                    btn7.setEnabled(false);
                    allX.add(7);
                    allTotal.add(7);
                    checkWin();
                    playSound(mCrossSound);
                    isHodCross = false;
                } else {
                    setZero(btn7);
                    btn7.setEnabled(false);
                    all0.add(7);
                    allTotal.add(7);
                    checkWinZero();
                    playSound(mZeroSound);
                    isHodCross = true;
                }
                break;
            case R.id.btn8:
                if (isHodCross) {
                    setCross(btn8);
                    btn8.setEnabled(false);
                    allX.add(8);
                    allTotal.add(8);
                    checkWin();
                    playSound(mCrossSound);
                    isHodCross = false;
                } else {
                    setZero(btn8);
                    btn8.setEnabled(false);
                    all0.add(8);
                    allTotal.add(8);
                    checkWinZero();
                    playSound(mZeroSound);
                    isHodCross = true;
                }
                break;
            case R.id.btn9:
                if (isHodCross) {
                    setCross(btn9);
                    btn9.setEnabled(false);
                    allX.add(9);
                    allTotal.add(9);
                    checkWin();
                    playSound(mCrossSound);
                    isHodCross = false;
                } else {
                    setZero(btn9);
                    btn9.setEnabled(false);
                    all0.add(9);
                    allTotal.add(9);
                    checkWinZero();
                    playSound(mZeroSound);
                    isHodCross = true;
                }
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
            tv1.setText("Xод 0!");

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (sumArrayList() < 45) {

                    } else {
                        draw();
                    }
                }
            }, 1500);
        }
    }

    public void runWin() {
        playSound(mWinSound);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                youWin();
            }
        }, 1500);
    }

    private int sumArrayList() {
        int sum = 0;

        for (int index : allTotal) {
            sum += index;
        }
        return sum;
    }

    private void checkWinZero() {
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
            tv1.setText("Ход Х!");
            buttonsEnable();
        }
    }

    public void runLoose() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                winZero();
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

    public void youWin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TwoPlayerActivity.this);
        builder.setTitle("Победа Х!")
                .setMessage("Выиграл игрок Х!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Начать заново",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                restartActivity(TwoPlayerActivity.this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(TwoPlayerActivity.this);
        builder.setTitle("Ничья!")
                .setMessage("Ничья!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Начать заново",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                restartActivity(TwoPlayerActivity.this);
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

    private void winZero() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TwoPlayerActivity.this);
        builder.setTitle("Победа 0!")
                .setMessage("Выиграл игрок 0")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Начать заново",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                restartActivity(TwoPlayerActivity.this);
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
        // return false;
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