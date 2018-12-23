package com.while1.myweek;

public class ItemListView {

	private String texto;
	private int ordem;
	private int semana;
	private boolean done;

	public ItemListView() {
	}

	public ItemListView(int semana, int ordem, String texto, boolean done) {
		this.setSemana(semana);
		this.ordem = ordem;
		this.texto = texto;
		this.setDone(done);
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public int getSemana() {
		return semana;
	}

	public void setSemana(int semana) {
		this.semana = semana;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
	public int getDone(){
		if (done)
			return 1;
		else
			return 0;
	}
}
