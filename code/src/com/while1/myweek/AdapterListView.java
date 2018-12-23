package com.while1.myweek;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

public class AdapterListView extends BaseAdapter {

	private int posicao;
	private LayoutInflater mInflater;
	private ArrayList<ItemListView> itens;
	private int idDia;
	private int count;
	private Context contxt;

	public AdapterListView(Context context, ArrayList<ItemListView> itens) {
		// Itens que preencheram o listview
		this.itens = itens;
		// responsavel por pegar o Layout do item.
		mInflater = LayoutInflater.from(context);

		posicao = -1;
	}

	public AdapterListView(Context context, ArrayList<ItemListView> itens,
			int dia) {
		// Itens que preencheram o listview
		this.itens = itens;
		// responsavel por pegar o Layout do item.
		mInflater = LayoutInflater.from(context);
		
		contxt = context;

		this.idDia = dia;

		posicao = -1;
	}

	public ArrayList<ItemListView> getItens() {
		return itens;
	}

	/**
	 * Retorna a quantidade de itens
	 * 
	 * @return
	 */
	public int getCount() {
		return itens.size();
	}

	/**
	 * Retorna o item de acordo com a posicao dele na tela.
	 * 
	 * @param position
	 * @return
	 */
	public ItemListView getItem(int position) {
		return itens.get(position);
	}

	/**
	 * Sem implementação
	 * 
	 * @param position
	 * @return
	 */
	public long getItemId(int position) {
		return position;
	}

	public void setPosicao(int pos) {
		if(posicao==pos){
			posicao = -1;
		}
		else{
			posicao = pos;
		}
	}

	static class ViewHolder {
		protected TextView text;
		protected ImageButton button_conc;
		protected ImageButton button_edit;
		protected ImageButton button_up;
		protected ImageButton button_down;
		protected ImageButton button_lixo;
	}

	public View getView(int position, View view, ViewGroup parent) {
		ItemListView item = itens.get(position);
		final ViewHolder viewHolder = new ViewHolder();
		final int ordem = position + 1;
		final int feito = item.getDone();
		final String texto = item.getTexto();
		Typeface type = Typeface.createFromAsset(contxt.getAssets(),"fonts/calibri.ttf");

		DbHelper dbHelper = new DbHelper(mInflater.getContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor result = db.rawQuery("SELECT * FROM tarefas WHERE mweek_week = "
				+ idDia, null);
		count = result.getCount();
		db.close();
		dbHelper.close();
		
		//Toast.makeText(mInflater.getContext(), String.valueOf(posicao), Toast.LENGTH_SHORT).show();

		if (posicao == -1) {
			view = mInflater.inflate(R.layout.item_listview, null);
			//((TextView) view.findViewById(R.id.item)).setText(String
			//		.valueOf(ordem) + ". " + item.getTexto());
			((TextView) view.findViewById(R.id.item)).setText(item.getTexto());
			((TextView) view.findViewById(R.id.item)).setTypeface(type);
			if (item.getDone() > 0) {
				((TextView) view.findViewById(R.id.item))
						.setPaintFlags(((TextView) view.findViewById(R.id.item))
								.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
				((TextView) view.findViewById(R.id.item)).setTextColor(0xFF999999);	
			}
		} else if (posicao == 0) {
			view = mInflater.inflate(R.layout.item_listview_selected, null);
			((TextView) view.findViewById(R.id.item_selected)).setText(item.getTexto());
			((TextView) view.findViewById(R.id.item_selected)).setTypeface(type);
			viewHolder.button_conc = (ImageButton) view
					.findViewById(R.id.concluido);
			viewHolder.button_edit = (ImageButton) view
					.findViewById(R.id.editar);
			viewHolder.button_up = (ImageButton) view.findViewById(R.id.up);
			viewHolder.button_down = (ImageButton) view.findViewById(R.id.down);
			viewHolder.button_lixo = (ImageButton) view.findViewById(R.id.lixo);
			viewHolder.text = (TextView) view.findViewById(R.id.item_selected);

			posicao = posicao - 1;

			if (ordem == 1) {
				viewHolder.button_up.setEnabled(false);
			}
			if (ordem == count) {
				viewHolder.button_down.setEnabled(false);
			}
			
			if (item.getDone() > 0) {
				((TextView) view.findViewById(R.id.item_selected))
						.setPaintFlags(((TextView) view.findViewById(R.id.item_selected))
								.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
				((TextView) view.findViewById(R.id.item_selected)).setTextColor(0xFF999999);
				((TextView) view.findViewById(R.id.item_selected)).setTypeface(type);
			}

			viewHolder.button_conc.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					DbHelper dbHelper = new DbHelper(mInflater.getContext());
					SQLiteDatabase db = dbHelper.getWritableDatabase();

					dbHelper.done(db, idDia, ordem, feito);

					db.close();
					dbHelper.close();

					ItemListView itemTmp = itens.get(ordem - 1);
					if(feito==0){
						itemTmp.setDone(true);						
					}
					else{
						itemTmp.setDone(false);
					}
					itens.set(ordem - 1, itemTmp);
					
					notifyDataSetChanged();
				}
			});
			
			viewHolder.button_edit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder alert = new AlertDialog.Builder(mInflater.getContext());
					alert.setTitle("Editar:");
					final EditText input = new EditText(mInflater.getContext());
					input.setText(texto);
					alert.setView(input);
					alert.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									String tarefa = input.getText().toString();

									DbHelper dbHelper = new DbHelper(mInflater.getContext());
									SQLiteDatabase db = dbHelper.getWritableDatabase();

									dbHelper.editar(db, idDia, ordem, tarefa);

									db.close();
									dbHelper.close();
									
									ItemListView itemTmp = itens.get(ordem - 1);
									itemTmp.setTexto(tarefa);
									itens.set(ordem - 1, itemTmp);
									
									notifyDataSetChanged();
								}
							});

					alert.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// Canceled.
								}
							});

					input.requestFocus();
					alert.show();
				}
			});

			viewHolder.button_up.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (ordem > 1) {
						DbHelper dbHelper = new DbHelper(mInflater.getContext());
						SQLiteDatabase db = dbHelper.getWritableDatabase();

						dbHelper.up(db, idDia, ordem);

						db.close();
						dbHelper.close();

						ItemListView itemTmp = itens.get(ordem - 2);
						itens.set(ordem - 2, itens.get(ordem - 1));
						itens.set(ordem - 1, itemTmp);

						notifyDataSetChanged();
					}
				}
			});

			viewHolder.button_down.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (ordem < count) {
						DbHelper dbHelper = new DbHelper(mInflater.getContext());
						SQLiteDatabase db = dbHelper.getWritableDatabase();

						dbHelper.down(db, idDia, ordem);

						db.close();
						dbHelper.close();

						ItemListView itemTmp = itens.get(ordem);
						itens.set(ordem, itens.get(ordem - 1));
						itens.set(ordem - 1, itemTmp);

						notifyDataSetChanged();
					}
				}
			});

			viewHolder.button_lixo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					DbHelper dbHelper = new DbHelper(mInflater.getContext());
					SQLiteDatabase db = dbHelper.getWritableDatabase();

					db.delete(dbHelper.TABLE, dbHelper.C_NUMBER + " = "
							+ String.valueOf(ordem) + " AND " + dbHelper.C_WEEK
							+ " = " + String.valueOf(idDia), null);

					db.close();
					dbHelper.close();

					posicao = -1;
					itens.remove(ordem - 1);

					notifyDataSetChanged();
				}
			});

			view.setTag(viewHolder);
		} else {
			view = mInflater.inflate(R.layout.item_listview, null);
			((TextView) view.findViewById(R.id.item)).setText(item.getTexto());
			
			if (item.getDone() > 0) {
				((TextView) view.findViewById(R.id.item))
						.setPaintFlags(((TextView) view.findViewById(R.id.item))
								.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
				((TextView) view.findViewById(R.id.item)).setTextColor(0xFF999999);
			}
			
			((TextView) view.findViewById(R.id.item)).setTypeface(type);
			
			posicao = posicao - 1;
		}
		// ((ImageView)
		// view.findViewById(R.id.imagemview)).setImageResource(item.getIconeRid());

		return view;
	}
}
