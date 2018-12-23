package com.example.my.week;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.*;
import android.content.*;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button btSeg = (Button) findViewById(R.id.button1);
        Button btTer = (Button) findViewById(R.id.Button01);
        Button btQua = (Button) findViewById(R.id.Button02);
        Button btQui = (Button) findViewById(R.id.Button03);
        Button btSex = (Button) findViewById(R.id.Button04);
        Button btSab = (Button) findViewById(R.id.Button05);
        Button btDom = (Button) findViewById(R.id.Button06);
        Button btMais = (Button) findViewById(R.id.Button07);
        
        btSeg.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Dia diaSeg = new Dia(0);
				Intent intent = new Intent(MainActivity.this, DiaActivity.class);
				intent.putExtra("idDia", 0);
				startActivity(intent);
			}
		});
        btTer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, DiaActivity.class);
				intent.putExtra("idDia", 1);
				startActivity(intent);
			}
		});
        btQua.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, DiaActivity.class);
				intent.putExtra("idDia", 2);
				startActivity(intent);
			}
		});
        btQui.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, DiaActivity.class);
				intent.putExtra("idDia", 3);
				startActivity(intent);
			}
		});
        btSex.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, DiaActivity.class);
				intent.putExtra("idDia", 4);
				startActivity(intent);
			}
		});
        btSab.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, DiaActivity.class);
				intent.putExtra("idDia", 5);
				startActivity(intent);
			}
		});
        btDom.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, DiaActivity.class);
				intent.putExtra("idDia", 6);
				startActivity(intent);
			}
		});
        btMais.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast toast = Toast.makeText(getApplicationContext(), "Desabilitado.", Toast.LENGTH_SHORT);
	            toast.show();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    	case R.id.menu_sair:
    		finish();
    		return true;
		default:
			return super.onOptionsItemSelected(item);
    	}
    	
    }
}
