package app.pitanje;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import app.db.Database;
import app.odgovor.Odgovor;
import app.pitanje.PitanjeDao;




public class PitanjeDao {

    public static ArrayList<Pitanje> getQuestions() {
    	ArrayList<Pitanje> pitanja = new ArrayList<>();
    	Database db = Database.getInstance();
    	try (Connection connection = db.connect()){
            PreparedStatement pstmt = connection.prepareStatement
            		("SELECT idPitanje, tekstPitanje, idOdgovor, tekstOdgovor, intenzitetOdgovor "
            				+ "FROM pitanje JOIN odgovor USING(idPitanje);");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Integer> appeared = new ArrayList<>();
            while (rs.next()) {
            	int idPitanje = rs.getInt("idPitanje");
            	if(!appeared.contains(idPitanje)) {
            		appeared.add(idPitanje);
                	String tekstPitanje = rs.getString("tekstPitanje");
                	ArrayList<Odgovor> odgovori = new ArrayList<Odgovor>();
                	PreparedStatement pstmtin = connection.prepareStatement
                   		("SELECT idPitanje, tekstPitanje, idOdgovor, tekstOdgovor, intenzitetOdgovor "
                    				+ "FROM pitanje JOIN odgovor USING(idPitanje)");	
                	ResultSet rsin = pstmtin.executeQuery();
                	while(rsin.next()) {
                		if(rsin.getInt("idPitanje") == idPitanje) {
                			odgovori.add(new Odgovor(rsin.getInt("idOdgovor"), idPitanje, 
                					rsin.getString("tekstOdgovor"), rsin.getInt("intenzitetOdgovor")));
                		}
                	}
                	pstmtin.close();
                	pitanja.add(new Pitanje(idPitanje, tekstPitanje, odgovori));
            	}
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return pitanja;
    }

}
