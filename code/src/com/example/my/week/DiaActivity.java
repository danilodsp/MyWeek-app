package com.example.my.week;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class DiaActivity extends Activity {

	int idDia;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia);
        
        Button btVoltar = (Button) findViewById(R.id.voltar);
        
        idDia = this.getIntent().getIntExtra("idDia", 0);
        
        switch (idDia){
        case 0:
        	this.setTitle("My Week >> Segunda-Feira");
        	break;
        case 1:
        	this.setTitle("My Week >> Terça-Feira");
        	break;
        case 2:
        	this.setTitle("My Week >> Quarta-Feira");
        	break;
        case 3:
        	this.setTitle("My Week >> Quinta-Feira");
        	break;
        case 4:
        	this.setTitle("My Week >> Sexta-Feira");
        	break;
        case 5:
        	this.setTitle("My Week >> Sábado");
        	break;
        case 6:
        	this.setTitle("My Week >> Domingo");
        	break;
        }
        
        btVoltar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_dia, menu);
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
