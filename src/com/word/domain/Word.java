package com.word.domain;

public class Word {
	private int Id;
	private int Id_pos;
	private String Mianownik;
	private String Dopelniacz;
	private String Wolacz;
	
	
	
	public Word() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Word(int id, int id_pos, String mianownik, String dopelniacz,
			String wolacz) {
		super();
		Id = id;
		Id_pos = id_pos;
		Mianownik = mianownik;
		Dopelniacz = dopelniacz;
		Wolacz = wolacz;
	}

	public int getId_pos() {
		return Id_pos;
	}

	public void setId_pos(int id_pos) {
		Id_pos = id_pos;
	}

	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getMianownik() {
		return Mianownik;
	}
	public void setMianownik(String mianownik) {
		Mianownik = mianownik;
	}
	public String getDopelniacz() {
		return Dopelniacz;
	}
	public void setDopelniacz(String dopelniacz) {
		Dopelniacz = dopelniacz;
	}
	public String getWolacz() {
		return Wolacz;
	}
	public void setWolacz(String wolacz) {
		Wolacz = wolacz;
	}
	
}
