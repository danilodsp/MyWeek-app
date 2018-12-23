package com.while1.myweek;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Log;
import java.lang.Math;
import java.util.Calendar;

public class WeekActivity extends Activity {

	int atualDia;
	int centerX;
	int centerY;
	float X;
	float Y;
	float referenciaX;
	float referenciaY;
	int raio;
	double mod;
	double modReferencia;
	double angulo;
	double faixaAngulo;
	ImageView btWeek;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_week);
		
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setIcon(R.drawable.icone2);
		
		btWeek = (ImageView) findViewById(R.id.week);
		atualDia = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

		switch(atualDia){
		case 1:
			this.setTitle(" (Domingo)");
			break;
		case 2:
			this.setTitle(" (Segunda)");
			break;
		case 3:
			this.setTitle(" (Terça)");
			break;
		case 4:
			this.setTitle(" (Quarta)");
			break;
		case 5:
			this.setTitle(" (Quinta)");
			break;
		case 6:
			this.setTitle(" (Sexta)");
			break;
		case 7:
			this.setTitle(" (Sábado)");
			break;
		}
		
		btWeek.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					centerX = btWeek.getWidth()/2;
					centerY = btWeek.getHeight()/2;
					if(raio==0){
						raio = btWeek.getWidth()/2;
					}
					faixaAngulo = (2*Math.PI)/7;
					referenciaX = 0;
					referenciaY = raio;
					modReferencia = Math.sqrt(Math.pow(referenciaX,2) + Math.pow(referenciaY,2));
					X = event.getX() - centerX;
					Y = (-1)*(event.getY() - centerY);
					mod = Math.sqrt(Math.pow(X,2) + Math.pow(Y,2));
					angulo = Math.acos(((X * referenciaX) + (Y * referenciaY)) / (mod * modReferencia));

					if(mod < raio){
						//Toast.makeText(v.getContext(), "Angulo:" + String.valueOf(angulo) + " X:" + String.valueOf(X) + " Y:" + String.valueOf(Y), Toast.LENGTH_SHORT).show();
						
						if(X < 0){
							if((angulo > 0)&&(angulo < faixaAngulo)){
								Intent intent = new Intent(WeekActivity.this,
										DayActivity.class);
								intent.putExtra("idDia", 6);
								startActivity(intent);
							}
							else if((angulo > faixaAngulo)&&(angulo < 2*faixaAngulo)){
								Intent intent = new Intent(WeekActivity.this,
										DayActivity.class);
								intent.putExtra("idDia", 5);
								startActivity(intent);
							}
							else if((angulo > 2*faixaAngulo)&&(angulo < 3*faixaAngulo)){
								Intent intent = new Intent(WeekActivity.this,
										DayActivity.class);
								intent.putExtra("idDia", 4);
								startActivity(intent);
							}
							else if((angulo > 3*faixaAngulo)&&(angulo < 4*faixaAngulo)){
								Intent intent = new Intent(WeekActivity.this,
										DayActivity.class);
								intent.putExtra("idDia", 3);
								startActivity(intent);
							}
						}
						else{
							if((angulo > 0)&&(angulo < faixaAngulo)){
								Intent intent = new Intent(WeekActivity.this,
										DayActivity.class);
								intent.putExtra("idDia", 0);
								startActivity(intent);
							}
							else if((angulo > faixaAngulo)&&(angulo < 2*faixaAngulo)){
								Intent intent = new Intent(WeekActivity.this,
										DayActivity.class);
								intent.putExtra("idDia", 1);
								startActivity(intent);
							}
							else if((angulo > 2*faixaAngulo)&&(angulo < 3*faixaAngulo)){
								Intent intent = new Intent(WeekActivity.this,
										DayActivity.class);
								intent.putExtra("idDia", 2);
								startActivity(intent);
							}
							else if((angulo > 3*faixaAngulo)&&(angulo < 4*faixaAngulo)){
								Intent intent = new Intent(WeekActivity.this,
										DayActivity.class);
								intent.putExtra("idDia", 3);
								startActivity(intent);
							}
						}
					}
				}
				return false;
			}
		});

	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    
	    if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
	    {
	    	raio = btWeek.getHeight()/2;
	        //Toast.makeText(this, "portrait", Toast.LENGTH_LONG).show();
	        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    }
	    else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
	    {
	    	raio = btWeek.getWidth()/2;
	        //Toast.makeText(this, "landscape", Toast.LENGTH_LONG).show();
	        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	    } 

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_week, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.sair_settings:
			this.finish();
			super.onDestroy();
			return true;
		case R.id.limpar_settings:
			limpar_banco();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void limpar_banco() {
		DbHelper dbHelper = new DbHelper(WeekActivity.this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		dbHelper.removeAll(db);

		db.close();
		dbHelper.close();
	}

}
