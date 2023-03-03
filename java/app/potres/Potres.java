package app.potres;



public class Potres {
    String idPotres;
    int intenzitet;
    float magnituda;
    String nazivPotres;
    String datum;
    String vrijeme;
    int novi;

    public Potres(String idPotres, int intenzitet, float magnituda, String nazivPotres, String datum, String vrijeme, int novi) {
        this.idPotres = idPotres;
        this.magnituda = magnituda;
        this.nazivPotres = nazivPotres;
        this.datum = datum;
        this.vrijeme = vrijeme;
        this.novi = novi;
        this.intenzitet = intenzitet;
    }


    public String getIdPotres() {
        return idPotres;
    }

    public int getIntenzitet() {
        return intenzitet;
    }

    public void setIntenzitet(int intenzitet) {
        this.intenzitet = intenzitet;
    }

    public float getMagnituda() {
        return magnituda;
    }

    public String getDatum() {
        return datum;
    }


    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }


    public void setMagnituda(float magnituda) {
        this.magnituda = magnituda;
    }

    public String getNazivPotres() {
        return nazivPotres;
    }

    

    public int calculateIntenzitet() {
        return PotresDao.calculateIntenzitet(idPotres);
    }

    
}
