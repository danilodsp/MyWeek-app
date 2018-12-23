package com.while1.myweek;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	private static final String TAG = DbHelper.class.getSimpleName();

	public static final String DB_NAME = "banco.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE = "tarefas";
	public static final String C_ID = BaseColumns._ID;
	public static final String C_WEEK = "mweek_week";
	public static final String C_NUMBER = "mweek_number";
	public static final String C_TEXT = "mweek_text";
	public static final String C_DONE = "mweek_done";

	Context context;
	ItemListView item;

	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String
				.format("Create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INT, %s INT, %s TEXT, %s INT)",
						TABLE, C_ID, C_WEEK, C_NUMBER, C_TEXT, C_DONE);

		Log.d(TAG, "onCreate sql: " + sql);

		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + TABLE);
		Log.d(TAG, "onUpgrade dropped table");
		this.onCreate(db);
	}

	public void insertData(SQLiteDatabase db, ItemListView item) {
		this.item = item;

		ContentValues values = new ContentValues();

		values.put(C_WEEK, item.getSemana());
		values.put(C_NUMBER, item.getOrdem());
		values.put(C_TEXT, item.getTexto());
		values.put(C_DONE, item.getDone());

		db.insert(TABLE, null, values);
	}

	public void up(SQLiteDatabase db, int idDia, int posicao) {
		// Passo 1
		String sql = String.format(
				"Update %s set %s = %s, %s = %s where %s = %s AND %s = %s",
				TABLE, C_NUMBER, String.valueOf(posicao - 1), C_WEEK,
				String.valueOf(10), C_WEEK, String.valueOf(idDia), C_NUMBER,
				String.valueOf(posicao));
		db.execSQL(sql);

		// Passo 2
		String sql2 = String.format(
				"Update %s set %s = %s where %s = %s AND %s = %s", TABLE,
				C_NUMBER, String.valueOf(posicao), C_WEEK,
				String.valueOf(idDia), C_NUMBER, String.valueOf(posicao - 1));
		db.execSQL(sql2);

		// Passo 3
		String sql3 = String.format("Update %s set %s = %s where %s = %s",
				TABLE, C_WEEK, String.valueOf(idDia), C_WEEK,
				String.valueOf(10));
		db.execSQL(sql3);
	}

	public void down(SQLiteDatabase db, int idDia, int posicao) {
		// Passo 1
		String sql = String.format(
				"Update %s set %s = %s, %s = %s where %s = %s AND %s = %s",
				TABLE, C_NUMBER, String.valueOf(posicao + 1), C_WEEK,
				String.valueOf(11), C_WEEK, String.valueOf(idDia), C_NUMBER,
				String.valueOf(posicao));
		db.execSQL(sql);

		// Passo 2
		String sql2 = String.format(
				"Update %s set %s = %s where %s = %s AND %s = %s", TABLE,
				C_NUMBER, String.valueOf(posicao), C_WEEK,
				String.valueOf(idDia), C_NUMBER, String.valueOf(posicao + 1));
		db.execSQL(sql2);

		// Passo 3
		String sql3 = String.format("Update %s set %s = %s where %s = %s",
				TABLE, C_WEEK, String.valueOf(idDia), C_WEEK,
				String.valueOf(11));
		db.execSQL(sql3);
	}

	public void done(SQLiteDatabase db, int idDia, int posicao, int feito) {
		String sql;
		if (feito == 0) {
			sql = String.format(
					"Update %s set %s = %s where %s = %s AND %s = %s", TABLE,
					C_DONE, String.valueOf(1), C_WEEK, String.valueOf(idDia),
					C_NUMBER, String.valueOf(posicao));
		} else {
			sql = String.format(
					"Update %s set %s = %s where %s = %s AND %s = %s", TABLE,
					C_DONE, String.valueOf(0), C_WEEK, String.valueOf(idDia),
					C_NUMBER, String.valueOf(posicao));
		}
		db.execSQL(sql);
	}
	
	public void removeAll(SQLiteDatabase db){
		String sql = String.format("Delete from %s", TABLE);
		db.execSQL(sql);
	}

	public void editar(SQLiteDatabase db, int idDia, int posicao, String texto){
		String sql = String.format(
				"Update %s set %s = '%s' where %s = %s AND %s = %s",
				TABLE, C_TEXT, texto, C_WEEK, String.valueOf(idDia), C_NUMBER,
				String.valueOf(posicao));
		db.execSQL(sql);
	}
}
