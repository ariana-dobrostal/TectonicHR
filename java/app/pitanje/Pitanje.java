package app.pitanje;

import java.util.ArrayList;

import app.odgovor.Odgovor;

public class Pitanje {
    private int idPitanje;
    private String tekstPitanje;
    private ArrayList<Odgovor> odgovori;
    public Pitanje(int idPitanje, String tekstPitanje, ArrayList<Odgovor> odgovori) {
        this.idPitanje = idPitanje;
        this.tekstPitanje = tekstPitanje;
        this.odgovori = odgovori;
    }
    public int getIdPitanje() {
        return idPitanje;
    }
 
    public String getTekstPitanje() {
        return tekstPitanje;
    }  
     
    public ArrayList<Odgovor> GetOdgovori() {
        return this.odgovori;
    }
}
