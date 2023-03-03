package app.upitnik;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.RowFilter.Entry;

import app.db.Database;
import app.potres.PotresDao;
import spark.embeddedserver.jetty.EmbeddedJettyFactory;

public class UpitnikDao {
    public static ArrayList<Upitnik> getUpitnici() {
        ArrayList<Upitnik> upitnici = new ArrayList<>();
        Database db = Database.getInstance();
        Connection connection = db.connect();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM upitnik join mjesto using(nazivMjesto);");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // String ime = rs.getString("ime");
                // String prezime = rs.getString("prezime");
                int idUpitnik = rs.getInt("idupitnik");
                int intenzitetUpitnik = rs.getInt("intenzitetupitnik");
                String datum = rs.getString("datum");
                String vrijeme = rs.getString("vrijeme");
                // int idPotres = rs.getInt("idpotres");
                String nazivMjesto = rs.getString("Nazivmjesto");
                // String mjesto = rs.getString("nazivmjesto");
                Upitnik upitnik = new Upitnik(idUpitnik, intenzitetUpitnik, datum, vrijeme, nazivMjesto);
                upitnici.add(upitnik);
            }
            pstmt.close();
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return upitnici;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return upitnici;
    }

    public static ArrayList<Upitnik> getNoviUpitnici() {
        ArrayList<Upitnik> noviUpitnici = new ArrayList<>();
        Database db = Database.getInstance();
        Connection connection = db.connect();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM upitnik WHERE idPotres IS NULL;");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int idUpitnik = rs.getInt("idupitnik");
                int intenzitetUpitnik = rs.getInt("intenzitetupitnik");
                String datum = rs.getString("datum");
                String vrijeme = rs.getString("vrijeme");
                String mjesto = rs.getString("nazivmjesto");
                Upitnik upitnik = new Upitnik(idUpitnik, intenzitetUpitnik, datum, vrijeme, mjesto);
                noviUpitnici.add(upitnik);
            }
            pstmt.close();
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return noviUpitnici;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return noviUpitnici;
    }

    /**
     * Metoda povezuje potres s ispunjenim upitnicima na način da atribut idPotres u
     * tablici Upitnik postavi na id potresa za koji je ispunjen.
     */
    public static void putUpitnikPotres(ArrayList<String> upitnici, int idPotres) {
        Database db = Database.getInstance();
        Connection connection = db.connect();
        int len = upitnici.size();
        Object[] upitniciArray = new Object[len];
        for (int i = 0; i < len; i++) {
            upitniciArray[i] = Integer.parseInt(upitnici.get(i));
        }
        try {
            String stmt = String.format("UPDATE upitnik SET idPotres = ? WHERE idUpitnik IN (%s)",
                    upitnici.stream()
                            .map(v -> "?")
                            .collect(Collectors.joining(", ")));
            PreparedStatement pstmt = connection.prepareStatement(stmt);
            pstmt.setLong(1, idPotres);
            int index = 2;
            for (String id : upitnici) {
                pstmt.setLong(index, Integer.parseInt(id));
                index++;
            }
            pstmt.executeUpdate();
            pstmt.close();
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getPitanjaIOdgovori(int idUpitnik) {
        List<String> pitanjaIOdgovori = new ArrayList<>();
        Database db = Database.getInstance();
        try (Connection connection = db.connect()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT tekstpitanje, tekstodgovor FROM upitnik NATURAL JOIN ispunjeni_upitnici NATURAL JOIN pitanje NATURAL JOIN odgovor WHERE idupitnik = ?;");
            pstmt.setInt(1, idUpitnik);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String pitanjeIOdgovor = rs.getString("tekstpitanje") + " " + rs.getString("tekstodgovor");
                pitanjaIOdgovori.add(pitanjeIOdgovor);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pitanjaIOdgovori;
    }

    public static List<String> getUpitniciInfo(String idPotres) {
        List<String> pitanjaIOdgovori = new ArrayList<>();
        Database db = Database.getInstance();
        try (Connection connection = db.connect()) {
            PreparedStatement pstmt2 = connection.prepareStatement(
                    "SELECT idupitnik FROM upitnik  WHERE idPotres = ?;");
            pstmt2.setInt(1, Integer.parseInt(idPotres));
            ResultSet rs2 = pstmt2.executeQuery();

            while (rs2.next()) {
                int idUpitnik = rs2.getInt("idupitnik");
                pitanjaIOdgovori.add("-----------upitnik broj " + idUpitnik + "-----------");
                PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT tekstpitanje, tekstodgovor FROM upitnik NATURAL JOIN ispunjeni_upitnici NATURAL JOIN pitanje NATURAL JOIN odgovor WHERE idupitnik = ?;");
                pstmt.setInt(1, idUpitnik);
                ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String pitanjeIOdgovor = rs.getString("tekstpitanje") + " " + rs.getString("tekstodgovor");
                pitanjaIOdgovori.add(pitanjeIOdgovor);
            }
            pstmt.close();
            pitanjaIOdgovori.add("\n");
            }
            pstmt2.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pitanjaIOdgovori;
    }

    public static int calculateIntenzitet(int idUpitnik) {
        Database database = Database.getInstance();

        float intensity = 0;

        try (Connection connection = database.connect()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT AVG(intenzitetodgovor) FROM ispunjeni_upitnici NATURAL JOIN odgovor WHERE idupitnik = ? GROUP BY idupitnik;");
            pstmt.setInt(1, idUpitnik);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                intensity = rs.getFloat("avg");
            }
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Math.round(intensity);
    }

    public static int addUpitnik(Upitnik upitnik) {
        Database database = Database.getInstance();
        int idUpitnik = 0;
        try (Connection connection = database.connect()) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
            java.sql.Date date = new java.sql.Date(sdf1.parse(upitnik.getDatum()).getTime());
            Time time = new java.sql.Time(sdf2.parse(upitnik.getVrijeme()).getTime());
            int idpotres;
            if (upitnik.getIdPotres() == 0) {
                idpotres = 0;
            } else {
                idpotres = upitnik.getIdPotres();
            }
            PreparedStatement pstmt_i = connection.prepareStatement("SELECT max(idUpitnik) FROM upitnik");
            ResultSet rs_i = pstmt_i.executeQuery();
            if(rs_i.next()) {
                idUpitnik = rs_i.getInt("max") + 1;
            }
            PreparedStatement pstmt = connection
                    .prepareStatement("INSERT INTO upitnik (idupitnik, intenzitetUpitnik, datum, vrijeme, idPotres, nazivMjesto) VALUES (?,?,?,?,?,?);");
                    pstmt.setInt(1, idUpitnik);
                    pstmt.setInt(2, upitnik.getIntenzitetUpitnik());
                    pstmt.setDate(3, date);
                    pstmt.setTime(4, time);
                    if(idpotres == 0) {
                        pstmt.setObject(5, null);;
                    }else {
                        pstmt.setInt(5, idpotres);
                    }
                    
                    pstmt.setString(6, upitnik.getNazivMjesto());

            
            pstmt.executeUpdate();
            if (upitnik.getIdPotres() != 0) {
                PotresDao.updateEarthquakeParams(upitnik.getIdPotres());
            } 
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return idUpitnik;
    }

    

    public static void deleteUpitnik(String idUpitnik) {
        Database db = Database.getInstance();
		Connection connection = db.connect();
		try {
			PreparedStatement pstmt = connection.prepareStatement("DELETE FROM upitnik WHERE idUpitnik = ?");
			pstmt.setInt(1, Integer.parseInt(idUpitnik));
			pstmt.executeUpdate();
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}	
    }
    
    public static ArrayList<Upitnik> getUpitniciPotres(String idPotres) {
        Database db = Database.getInstance();
        ArrayList<Upitnik> upitnici = new ArrayList<>();
		try (Connection connection = db.connect()){
			PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM upitnik WHERE idPotres = ?");
			pstmt.setInt(1, Integer.parseInt(idPotres));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int idUpitnik = rs.getInt("idupitnik");
                int intenzitetUpitnik = rs.getInt("intenzitetupitnik");
                String datum = rs.getString("datum");
                String vrijeme = rs.getString("vrijeme");
                String mjesto = rs.getString("nazivMjesto");
                Upitnik upitnik = new Upitnik(idUpitnik, intenzitetUpitnik, datum, vrijeme, mjesto);
                upitnici.add(upitnik);
            }
            pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
        return upitnici;
    }

    public static ArrayList<String> getMjesta() {
        Database db = Database.getInstance();
        ArrayList<String> mjesta= new ArrayList<>();
        try(Connection connection = db.connect()) {
            PreparedStatement pstmt = connection.prepareStatement("SELECT nazivmjesto FROM mjesto;");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                mjesta.add(rs.getString("nazivmjesto"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mjesta;
    }

    public static void addUpitnikData(HashMap<Integer, Integer> pitanje_intenzitet, int idUpitnik) {
        
        Database db = Database.getInstance();
        try(Connection connection = db.connect()) {
            PreparedStatement pstmt_ido = connection.prepareStatement("SELECT idodgovor FROM odgovor WHERE idpitanje = ? AND intenzitetodgovor = ?;");
            PreparedStatement pstmt_insert = connection.prepareStatement("INSERT INTO ispunjeni_upitnici (idupitnik, idodgovor, idpitanje) VALUES (?,?,?);");
            for(Map.Entry<Integer, Integer> entry : pitanje_intenzitet.entrySet()) {
                pstmt_ido.setInt(1, entry.getKey());
                pstmt_ido.setInt(2, entry.getValue());
                ResultSet rs = pstmt_ido.executeQuery();
                int idOdgovor = 0;
                if(rs.next()) {
                    idOdgovor = rs.getInt("idodgovor");
                }
               
                pstmt_insert.setInt(1, idUpitnik);
                pstmt_insert.setInt(2, idOdgovor);
                pstmt_insert.setInt(3, entry.getKey());
                pstmt_insert.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean upitnikExists(String id) {
        Database db = Database.getInstance();
        try (Connection connection = db.connect()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT idupitnik FROM upitnik WHERE idupitnik = ?;");
            pstmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                return true;
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
