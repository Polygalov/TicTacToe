package ua.com.adr.android.tictactoe;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Andy on 11.01.2018.
 */

public class StatisticFragment extends DialogFragment implements View.OnClickListener {

    TextView tvEasyXWin, tvEasyXLose, tvEasyXDraw;
    TextView tvHardXWin, tvHardXLose, tvHardXDraw;
    TextView tvEasy0Win, tvEasy0Lose, tvEasy0Draw;
    TextView tvHard0Win, tvHard0Lose, tvHard0Draw;
    TextView tvTotalWin, tvTotalLose, tvTotalDraw;
    final String LOG_TAG = "myLogs";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Статистика");
        View v = inflater.inflate(R.layout.statistic_dialog, null);
        v.findViewById(R.id.btnRestart).setOnClickListener(this);
        v.findViewById(R.id.btnExit).setOnClickListener(this);
        initialViews(v);
        DBHelper dbHelper = new DBHelper(getContext());
        setTableValue(dbHelper);
        return v;
    }

    public void initialViews(View v) {
        tvEasyXWin = (TextView) v.findViewById(R.id.score_easy_x_win);
        tvEasyXLose = (TextView) v.findViewById(R.id.score_easy_x_lose);
        tvEasyXDraw = (TextView) v.findViewById(R.id.score_easy_x_draw);
        tvHardXWin = (TextView) v.findViewById(R.id.score_hard_x_win);
        tvHardXLose = (TextView) v.findViewById(R.id.score_hard_x_lose);
        tvHardXDraw = (TextView) v.findViewById(R.id.score_hard_x_draw);
        tvEasy0Win = (TextView) v.findViewById(R.id.score_easy_0_win);
        tvEasy0Lose = (TextView) v.findViewById(R.id.score_easy_0_lose);
        tvEasy0Draw = (TextView) v.findViewById(R.id.score_easy_0_draw);
        tvHard0Win = (TextView) v.findViewById(R.id.score_hard_0_win);
        tvHard0Lose = (TextView) v.findViewById(R.id.score_hard_0_lose);
        tvHard0Draw = (TextView) v.findViewById(R.id.score_hard_0_draw);
        tvTotalWin = (TextView) v.findViewById(R.id.score_total_win);
        tvTotalLose = (TextView) v.findViewById(R.id.score_total_lose);
        tvTotalDraw = (TextView) v.findViewById(R.id.score_total_draw);

    }

    public void setTableValue(DBHelper dbHelper) {
        tvEasyXWin.setText("" + dbHelper.getValue("WIN", 1));
        tvEasyXLose.setText("" + dbHelper.getValue("LOSE", 1));
        tvEasyXDraw.setText("" + dbHelper.getValue("DRAW", 1));
        tvHardXWin.setText("" + dbHelper.getValue("WIN", 2));
        tvHardXLose.setText("" + dbHelper.getValue("LOSE", 2));
        tvHardXDraw.setText("" + dbHelper.getValue("DRAW", 2));
        tvEasy0Win.setText("" + dbHelper.getValue("WIN", 3));
        tvEasy0Lose.setText("" + dbHelper.getValue("LOSE", 3));
        tvEasy0Draw.setText("" + dbHelper.getValue("DRAW", 3));
        tvHard0Win.setText("" + dbHelper.getValue("WIN", 4));
        tvHard0Lose.setText("" + dbHelper.getValue("LOSE", 4));
        tvHard0Draw.setText("" + dbHelper.getValue("DRAW", 4));
        tvTotalWin.setText("" + dbHelper.getTotalOfAmount("WIN"));
        tvTotalLose.setText("" + dbHelper.getTotalOfAmount("LOSE"));
        tvTotalDraw.setText("" + dbHelper.getTotalOfAmount("DRAW"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRestart:
                resetDB();
                dismiss();
                break;
            case R.id.btnExit:
                dismiss();
                break;
        }
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 1: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 1: onCancel");
    }

    public void resetDB() {
        DBHelper dbHelper = new DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.updateTable(db, "Easy_X", 1, 0, 0, 0);
        dbHelper.updateTable(db, "Hard_X", 2, 0, 0, 0);
        dbHelper.updateTable(db, "Easy_0", 3, 0, 0, 0);
        dbHelper.updateTable(db, "Hard_0", 4, 0, 0, 0);

        db.close();
    }
}
