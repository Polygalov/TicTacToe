package ua.com.adr.android.tictactoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andy on 12.01.2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "statistic"; // Имя базы данных
    private static final int DB_VERSION = 1; // Версия базы данных

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TICTACTOE ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "WIN INTEGER, "
                + "LOSE INTEGER, "
                + "DRAW INTEGER);");
        insertData(db, "Easy_X", 0, 0, 0);
        insertData(db, "Hard_X", 0, 0, 0);
        insertData(db, "Easy_0", 0, 0, 0);
        insertData(db, "Hard_0", 0, 0, 0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(SQLiteDatabase db, String name,
                           int win, int lose, int draw) {
        ContentValues resultValues = new ContentValues();
        resultValues.put("NAME", name);
        resultValues.put("WIN", win);
        resultValues.put("LOSE", lose);
        resultValues.put("DRAW", draw);
        db.insert("TICTACTOE", null, resultValues);
    }

    // Updating single book
    public int updateTable(SQLiteDatabase db, String name, int id, int win, int lose, int draw) {

        ContentValues resultValues = new ContentValues();
        resultValues.put("NAME", name);
        resultValues.put("WIN", win);
        resultValues.put("LOSE", lose);
        resultValues.put("DRAW", draw);

        int i = db.update("TICTACTOE", //table
                resultValues, // column/value
                "_id" + " = ?", // selections
                new String[]{String.valueOf(id)}); //selection args

        return i;
    }

    public int getTotalOfAmount(String columnName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(" + columnName + ") FROM  TICTACTOE", null);
        c.moveToFirst();
        int i = c.getInt(0);
        c.close();
        return i;
    }

    public int getValue(String columnName, int id_level) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + columnName + " FROM TICTACTOE WHERE _id = " + id_level, null);
        c.moveToFirst();
        int j = c.getInt(0);
        c.close();
        return j;
    }
}
