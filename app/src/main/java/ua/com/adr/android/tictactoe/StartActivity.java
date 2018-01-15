package ua.com.adr.android.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ToggleButton;

public class StartActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    Button btn1player, btn2player;
    RadioButton rb_easy, rb_hard, rb_player, rb_android;
    ImageView exit;
    SharedPreferences sPref;
    final String SAVED_STATE = "saved_sound_state";
    ToggleButton tbIsSounds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btn1player = (Button) findViewById(R.id.btn1player);
        btn2player = (Button) findViewById(R.id.btn2player);
        exit = (ImageView) findViewById(R.id.exit);
        rb_easy = (RadioButton) findViewById(R.id.radioButtonEasy);
        rb_hard = (RadioButton) findViewById(R.id.radioButtonHard);
        rb_easy.setChecked(true);
        rb_player = (RadioButton) findViewById(R.id.rb_player);
        rb_android = (RadioButton) findViewById(R.id.rb_android);
        rb_player.setChecked(true);
        tbIsSounds = (ToggleButton) findViewById(R.id.toggleButton);

        btn1player.setOnClickListener(this);
        btn2player.setOnClickListener(this);
        exit.setOnClickListener(this);

        tbIsSounds.setOnCheckedChangeListener(this);

        LoadPreferences();
        tbIsSounds.setChecked(GlobalVars.isSounds);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked)
            GlobalVars.isSounds = true;
        else
            GlobalVars.isSounds = false;

        SavePreferences();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1player:
                boolean isEasy;
                if (rb_easy.isChecked()) {
                    isEasy = true;
                } else
                    isEasy = false;
                if (rb_player.isChecked()) {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    intent.putExtra("isEasy", isEasy);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(StartActivity.this, FirstZeroActivity.class);
                    intent.putExtra("isEasy", isEasy);
                    startActivity(intent);
                }
                break;
            case R.id.btn2player:
                Intent intent2 = new Intent(StartActivity.this, TwoPlayerActivity.class);
                startActivity(intent2);
                break;
            case R.id.exit:
                finishAffinity();
                break;

        }
    }

    public void SavePreferences() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean(SAVED_STATE, GlobalVars.isSounds);
        editor.apply();
    }

    private void LoadPreferences() {
        sPref = getPreferences(MODE_PRIVATE);
        boolean isSound = sPref.getBoolean(SAVED_STATE, true);
        GlobalVars.isSounds = isSound;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SavePreferences();
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        tbIsSounds.setChecked(GlobalVars.isSounds);
    }
}
