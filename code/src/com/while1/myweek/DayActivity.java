package com.while1.myweek;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

public class DayActivity extends Activity implements OnItemClickListener {

	private int posicao;
	private ListView listView;
	private AdapterListView adapterListView;
	private ArrayList<ItemListView> itens;

	int idDia;

	ImageButton btOk;
	ImageButton btEdit;
	ImageButton btUp;
	ImageButton btDown;
	ImageButton btLixo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day);
		
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setIcon(R.drawable.icone2);

		listView = (ListView) findViewById(R.id.list);

		idDia = this.getIntent().getIntExtra("idDia", 0);
		
		switch (idDia) {
		case 0:
			this.setTitle("Domingo");
			break;
		case 1:
			this.setTitle("Segunda-Feira");
			break;
		case 2:
			this.setTitle("Terça-Feira");
			break;
		case 3:
			this.setTitle("Quarta-Feira");
			break;
		case 4:
			this.setTitle("Quinta-Feira");
			break;
		case 5:
			this.setTitle("Sexta-Feira");
			break;
		case 6:
			this.setTitle("Sábado");
			break;
		}

		listView.setOnItemClickListener(this);
		createListView();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.day_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add:

			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Descrição:");
			final EditText input = new EditText(this);
			alert.setView(input);
			alert.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							String tarefa = input.getText().toString();
							addTarefa(tarefa);
						}
					});

			alert.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// Canceled.
						}
					});

			alert.show();

			return true;
		case R.id.lixo_tudo_menu:

			DbHelper dbHelper = new DbHelper(DayActivity.this);
			SQLiteDatabase db = dbHelper.getWritableDatabase();

			dbHelper.onUpgrade(db, 0, 0);

			itens = new ArrayList<ItemListView>();
			adapterListView = new AdapterListView(this, itens, idDia);
			listView.setAdapter(adapterListView);
			listView.setCacheColorHint(Color.TRANSPARENT);

			db.close();
			dbHelper.close();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void createListView() {
		boolean done;
		DbHelper dbHelper = new DbHelper(DayActivity.this);
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		Cursor result = db.rawQuery("SELECT * FROM " + dbHelper.TABLE
				+ " WHERE " + dbHelper.C_WEEK + " = " + idDia + " ORDER BY "
						+ dbHelper.C_NUMBER, null);

		itens = new ArrayList<ItemListView>();

		int count = result.getCount();

		if (count != 0) {
			result.moveToFirst();

			while (count != 0) {
				int id = result.getInt(0);
				int semana = result.getInt(1);
				int ordem = result.getInt(2);
				String texto = result.getString(3);
				int feito = result.getInt(4);
				if (feito == 0)
					done = false;
				else
					done = true;

				ItemListView itemNovo = new ItemListView(semana, ordem, texto,
						done);

				itens.add(itemNovo);

				result.moveToNext();
				count--;
			}
		}

		adapterListView = new AdapterListView(this, itens, idDia);

		listView.setAdapter(adapterListView);
		listView.setCacheColorHint(Color.TRANSPARENT);

		db.close();
		dbHelper.close();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		posicao = arg2;

		ItemListView item = adapterListView.getItem(posicao);
		adapterListView.setPosicao(posicao);

		listView.setAdapter(adapterListView);
		listView.setCacheColorHint(Color.TRANSPARENT);
		//Toast.makeText(this, "Você Clicou em: " + item.getTexto() + " no dia: " + String.valueOf(idDia), Toast.LENGTH_SHORT).show();
	}

	public void addTarefa(String tarefa) {
		itens = adapterListView.getItens();
		ItemListView itemNovo = new ItemListView(idDia,
				(adapterListView.getCount() + 1), tarefa, false);
		itens.add(itemNovo);

		DbHelper dbHelper = new DbHelper(DayActivity.this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		dbHelper.insertData(db, itemNovo);

		db.close();
		dbHelper.close();

		listView.setAdapter(adapterListView);
		listView.setCacheColorHint(Color.TRANSPARENT);
	}

}
