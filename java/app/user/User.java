package app.user;

import java.util.Objects;

public class User {
	private String ime;
	private String prezime;
	private String email;
	private String lozinka;
	private Uloga uloga;
	private boolean prva_prijava;
	

	public User(String ime, String prezime, String email, String lozinka, Uloga uloga, boolean prva_prijava) {
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.lozinka = lozinka;
		this.uloga = uloga;
		this.prva_prijava = prva_prijava;
	}
	
	public String getIme() {
		return ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public String getEmail() {
		return email;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}


	public Uloga getUloga() {
		return uloga;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}


	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	public boolean isAdmin() {
		return this.uloga == Uloga.ADMIN;
	}

	public boolean isPrva_prijava() {return prva_prijava; }


	public boolean checkPassword(String lozinka) {
		return this.lozinka.equals(lozinka);
	}
}
