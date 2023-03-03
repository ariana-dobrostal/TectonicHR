package app.odgovor;

public class Odgovor {
    private int idOdgovor;
    private int idPitanje;
    private String tekstOdgovor;
    private int intenzitetOdgovor;
    public Odgovor(int idOdgovor, int idPitanje, String tekstOdgovor, int intenzitetOdgovor) {
        this.idOdgovor = idOdgovor;
        this.idPitanje = idPitanje;
        this.tekstOdgovor = tekstOdgovor;
        this.intenzitetOdgovor = intenzitetOdgovor;
    }
    public int getIdOdgovor() {
        return idOdgovor;
    }
    
    public int getIdPitanje() {
        return idPitanje;
    }
    
    public String getTekstOdgovor() {
        return tekstOdgovor;
    }
    
    public int getIntenzitetOdgovor() {
        return intenzitetOdgovor;
    }
        
}
