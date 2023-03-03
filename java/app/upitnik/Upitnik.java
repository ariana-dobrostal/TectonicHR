package app.upitnik;


public class Upitnik {
    private int idUpitnik;
    private int intenzitetUpitnik;
    private String datum;
    private String vrijeme;
    private int idPotres;
    private String nazivMjesto;

    public Upitnik(int idUpitnik, int intenzitetUpitnik, String datum, String vrijeme, int idPotres, String nazivMjesto) {
        this.idUpitnik = idUpitnik;
        this.intenzitetUpitnik = intenzitetUpitnik;
        this.datum = datum;
        this.vrijeme = vrijeme;
        this.idPotres = idPotres;
        this.nazivMjesto = nazivMjesto;
    }

    public Upitnik(int idUpitnik, int intenzitetUpitnik, String datum, String vrijeme, String nazivMjesto) {
        this.idUpitnik = idUpitnik;
        this.intenzitetUpitnik = intenzitetUpitnik;
        this.datum = datum;
        this.vrijeme = vrijeme;
        this.nazivMjesto = nazivMjesto;
    }

    public int getIdUpitnik() {
        return idUpitnik;
    }


    public int getIntenzitetUpitnik() {
        return intenzitetUpitnik;
    }

    public void setIntenzitetUpitnik(int intenzitetUpitnik) {
        this.intenzitetUpitnik = intenzitetUpitnik;
    }

    public String getDatum() {
        return datum;
    }

    public String getVrijeme() {
        return vrijeme.substring(0,8);
    }

    public int getIdPotres() {
        return idPotres;
    }

    public void setIdPotres(int idPotres) {
        this.idPotres = idPotres;
    }

    public String getNazivMjesto() {
        return nazivMjesto;
    }

    public int calculateIntenzitet() {
       return UpitnikDao.calculateIntenzitet(idUpitnik);
    }

}
