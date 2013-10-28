package com.word.domain;

public class PartOfSpeech {
	private int id;
	private int id_pos;
	private String rzeczownik;
	private String przymiotnik;
	private String czasownik;
	private String mianoswnik;
	
	
	public int getId_pos() {
		return id_pos;
	}
	public void setId_pos(int id_pos) {
		this.id_pos = id_pos;
	}
	public String getMianoswnik() {
		return mianoswnik;
	}
	public void setMianoswnik(String mianoswnik) {
		this.mianoswnik = mianoswnik;
	}
	public String getDopelniacz() {
		return dopelniacz;
	}
	public void setDopelniacz(String dopelniacz) {
		this.dopelniacz = dopelniacz;
	}
	public String getWolacz() {
		return wolacz;
	}
	public void setWolacz(String wolacz) {
		this.wolacz = wolacz;
	}
	private String dopelniacz;
	private String wolacz;
	
	
	
	public PartOfSpeech() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PartOfSpeech(int id, String rzeczownik, String przymiotnik,
			String czasownik) {
		super();
		this.id = id;
		this.rzeczownik = rzeczownik;
		this.przymiotnik = przymiotnik;
		this.czasownik = czasownik;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRzeczownik() {
		return rzeczownik;
	}
	public void setRzeczownik(String rzeczownik) {
		this.rzeczownik = rzeczownik;
	}
	public String getPrzymiotnik() {
		return przymiotnik;
	}
	public void setPrzymiotnik(String przymiotnik) {
		this.przymiotnik = przymiotnik;
	}
	public String getCzasownik() {
		return czasownik;
	}
	public void setCzasownik(String czasownik) {
		this.czasownik = czasownik;
	}
	
	
	
	
}
