package app.potres;

import app.db.Database;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PotresDao {
    public static Potres getPotresById(int idPotres) {
        Database db = Database.getInstance();
        try (Connection connection = db.connect()) {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM potres WHERE idPotres = ?;");
            pstmt.setInt(1, idPotres);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int intenzitet = rs.getInt("intenzitet");
                float magnituda = rs.getFloat("magnituda");
                String naziv = rs.getString("nazivpotres");
                String datum = rs.getDate("datum").toString();
                String vrijeme = rs.getString("vrijeme");
                int novi = rs.getInt("novi");
                return new Potres(Integer.toString(idPotres), intenzitet, magnituda, naziv, datum, vrijeme,
                        novi);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Potres> getOldEarthquakes() {
        Database db = Database.getInstance();
        Connection connection = db.connect();
        ArrayList<Potres> potresi = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM potres WHERE novi = ?;");
            pstmt.setInt(1, 0);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String idPotres = rs.getString("idPotres");
                int intenzitet = rs.getInt("intenzitet");
                float magnituda = rs.getFloat("magnituda");
                String naziv = rs.getString("nazivpotres");
                String datum = rs.getDate("datum").toString();
                String vrijeme = rs.getString("vrijeme");
                int novi = rs.getInt("novi");
                if (intenzitet == 0)
                    intenzitet = calculateIntenzitet(idPotres);
                potresi.add(new Potres(idPotres, intenzitet, magnituda, naziv, datum, vrijeme, novi));
            }
            pstmt.close();
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return potresi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Potres> getRecentEarthquakes() {
        Database db = Database.getInstance();
        Connection connection = db.connect();
        ArrayList<Potres> potresi = new ArrayList<>();
        try {

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM potres WHERE novi = ?;");
            pstmt.setInt(1, 1);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String idPotres = rs.getString("idPotres");
                int intenzitet = rs.getInt("intenzitet");
                float magnituda = rs.getFloat("magnituda");
                String naziv = rs.getString("nazivpotres");
                String datum = rs.getDate("datum").toString();
                String vrijeme = rs.getString("vrijeme");
                int novi = rs.getInt("novi");
                if (intenzitet == 0)
                    intenzitet = calculateIntenzitet(idPotres);
                potresi.add(new Potres(idPotres, intenzitet, magnituda, naziv, datum, vrijeme, novi));

            }
            pstmt.close();
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return potresi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Potres> getAllEarthquakes() {
        Database db = Database.getInstance();
        Connection connection = db.connect();
        ArrayList<Potres> potresi = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM potres;");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String idPotres = rs.getString("idPotres");
                int intenzitet = rs.getInt("intenzitet");
                float magnituda = rs.getFloat("magnituda");
                String naziv = rs.getString("nazivpotres");
                String datum = rs.getDate("datum").toString();
                String vrijeme = rs.getString("vrijeme");
                int novi = rs.getInt("novi");
                if (intenzitet == 0)
                    intenzitet = calculateIntenzitet(idPotres);
                potresi.add(new Potres(idPotres, intenzitet, magnituda, naziv, datum, vrijeme, novi));
            }

            pstmt.close();
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return potresi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int addEarthquake(Potres potres) {
        // promijenjeno
        Database db = Database.getInstance();
        Connection connection = db.connect();
        int idPotres = 0;
        try {
            PreparedStatement pstmt_i = connection.prepareStatement("SELECT max(idPotres) FROM potres");
            ResultSet rs_i = pstmt_i.executeQuery();
            if (rs_i.next()) {
                idPotres = rs_i.getInt("max") + 1;
            }

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");

            Time time = new java.sql.Time(sdf2.parse(potres.getVrijeme()).getTime());
            Date date = new java.sql.Date(sdf1.parse(potres.getDatum()).getTime());
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO potres(idpotres, intenzitet, magnituda, nazivpotres, datum, vrijeme, novi) VALUES (?, ?, ?, ?, ?, ?, ?);");
            stmt.setInt(1, idPotres);
            stmt.setInt(2, potres.getIntenzitet());
            stmt.setFloat(3, potres.getMagnituda());
            stmt.setString(4, potres.getNazivPotres());
            stmt.setDate(5, date);
            stmt.setTime(6, time);
            stmt.setInt(7, 1);
            stmt.executeUpdate();
            stmt.close();
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
        return idPotres;
    }

    public static void deleteEarthquake(String id) {
        Database db = Database.getInstance();
        String sql = "DELETE FROM potres WHERE idPotres::text = ?";
        try (Connection connection = db.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
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

    public static void updateEarthquake(String id) {
        Database db = Database.getInstance();
        String sql = "UPDATE potres SET novi=0 WHERE idPotres::text = ?";
        try (Connection connection = db.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
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

    public static Map<String, Float> getCityAndIntensity(String idPotres) {
        Database database = Database.getInstance();

        Map<String, Float> result = new HashMap<>();

        try (Connection connection = database.connect()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT nazivmjesto, AVG(intenzitetupitnik) FROM upitnik NATURAL JOIN mjesto WHERE idpotres = ? GROUP BY nazivmjesto;");
            pstmt.setInt(1, Integer.parseInt(idPotres));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                result.put(rs.getString("nazivmjesto"), rs.getFloat("avg"));
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static int calculateIntenzitet(String idPotres) {
        Database database = Database.getInstance();
        double avgIntensityEpicenter = 0;

        try (Connection connection = database.connect()) {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT * FROM upitnik NATURAL JOIN mjesto WHERE idpotres = ?;");
            pstmt.setInt(1, Integer.parseInt(idPotres));
            ResultSet rs = pstmt.executeQuery();
            Map<String, Float> coordsEpicenter = calculateCoordsEpicenter(idPotres);
            int count = 0;
            float[] coordinatesEpicenter = new float[2];
            coordinatesEpicenter[0] = coordsEpicenter.get("lng");
            coordinatesEpicenter[1] = coordsEpicenter.get("lat");
            while (rs.next()) {
                float[] coordinatesPlace = getCoordinates(rs.getString("nazivmjesto"));

                double iMax = rs.getInt("intenzitetupitnik");
                double r = getDistance(coordinatesEpicenter, coordinatesPlace);
                double h = 10;
                double u = 0.4343;
                double a = 0.005;
                double intensityEpicenter;

                if (r != 0)
                    intensityEpicenter = iMax + 3 * Math.log(r / h) + 3 * u * a * (r - h);
                else
                    intensityEpicenter = iMax; // gornja formula ne vrijedi za dobivanje intenziteta iz epicentra jer je
                // log(0) nedefiniran (r = udaljenost = 0 za epicentar)
                avgIntensityEpicenter += intensityEpicenter;
                count++;
            }
            avgIntensityEpicenter /= count;

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (Math.round(avgIntensityEpicenter) > 12) {
            return 12;
        }
        return (int) Math.round(avgIntensityEpicenter);
    }

    public static String getDate(String idPotres) {
        Database database = Database.getInstance();

        String date = "";

        try (Connection connection = database.connect()) {
            PreparedStatement pstmt = connection.prepareStatement("SELECT datum FROM potres WHERE idpotres = ?;");
            pstmt.setInt(1, Integer.parseInt(idPotres));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                date = rs.getDate("datum").toString();

            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getTime(String idPotres) {
        Database database = Database.getInstance();

        String time = "";

        try (Connection connection = database.connect()) {
            PreparedStatement pstmt = connection.prepareStatement("SELECT vrijeme FROM potres WHERE idpotres = ?;");
            pstmt.setInt(1, Integer.parseInt(idPotres));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                time = rs.getTime("vrijeme").toString();

            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return time;
    }

    public static String getNazivPotres(String idPotres) {
        Database database = Database.getInstance();

        String nazivPotres = "";

        try (Connection connection = database.connect()) {
            PreparedStatement pstmt = connection.prepareStatement("SELECT nazivPotres FROM potres WHERE idpotres = ?;");
            pstmt.setInt(1, Integer.parseInt(idPotres));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                nazivPotres = rs.getString("nazivPotres");
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nazivPotres;
    }

    public static float[] getCoordinates(String cityName) {
        float[] coordinates = new float[2];
        Database database = Database.getInstance();
        try (Connection connection = database.connect()) {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM mjesto where nazivmjesto = ?;");
            pstmt.setString(1, cityName);
            ResultSet rs = pstmt.executeQuery();

            rs.next();
            coordinates[0] = rs.getFloat("long");
            coordinates[1] = rs.getFloat("lat");
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coordinates;
    }

    private static double getDistance(float[] coor1, float[] coor2) {
        double R = 6371000; // metres
        double fi1 = coor1[0] * Math.PI / 180;
        double fi2 = coor2[0] * Math.PI / 180;
        double deltaFi = (coor2[0] - coor1[0]) * Math.PI / 180;
        double deltaLambda = (coor2[1] - coor1[1]) * Math.PI / 180;

        double a = Math.sin(deltaFi / 2) * Math.sin(deltaFi / 2)
                + Math.cos(fi1) * Math.cos(fi2) * Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double d = R * c; // metres

        return d / 1000; // km
    }

    /**
     * Metoda se zove pri svakom novom unosu upitnika u bazu ili dodijeljivanju
     * upitnika potresu.
     * Računa intenzitet potresa prema dodijeljenim upitnicima.
     */
    public static void updateEarthquakeParams(Integer id) {
        Database db = Database.getInstance();

        try (Connection connection = db.connect()) {

            PreparedStatement pstmtIntenzitet = connection
                    .prepareStatement("UPDATE potres SET intenzitet = ? WHERE idPotres = ?;");
            pstmtIntenzitet.setFloat(1, calculateIntenzitet(id.toString()));
            pstmtIntenzitet.setLong(2, id);
            pstmtIntenzitet.executeUpdate();
            pstmtIntenzitet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Float> calculateCoordsEpicenter(String idPotres) {
        Database database = Database.getInstance();

        Map<String, Float> result = new HashMap<>();

        float sumLat = 0;
        float sumLng = 0;
        int num = 0;

        try (Connection connection = database.connect()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT nazivmjesto, AVG(intenzitetupitnik), mjesto.lat, mjesto.long FROM upitnik NATURAL JOIN mjesto WHERE idpotres = ? GROUP BY nazivmjesto, mjesto.lat, mjesto.long ORDER BY AVG(intenzitetupitnik) DESC LIMIT 3;");
            pstmt.setInt(1, Integer.parseInt(idPotres));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                sumLat += rs.getFloat("lat");
                sumLng += rs.getFloat("long");
                num++;
            }
            pstmt.close();
            result.put("lat", sumLat / num);
            result.put("lng", sumLng / num);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static float getMagnituda(String idPotres) {
        String dateDatabase = getDate(idPotres);
        java.util.Date datum_start = null;
        java.util.Date datum_end;
        String date_end = null;
        try {
            datum_start = new SimpleDateFormat("yyyy-MM-dd").parse(dateDatabase);
            datum_end = new Date(datum_start.getTime() + (1000 * 60 * 60 * 24));
            date_end = new SimpleDateFormat("yyyy-MM-dd").format(datum_end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        URI uri = URI.create("https://www.seismicportal.eu/fdsnws/event/1/query?limit=100&start=" + dateDatabase + "&end=" + date_end + "&minlat=41.858935&maxlat=46.554343&minlon=13.477641&maxlon=19.477005&format=text");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofSeconds(20))
                .GET()
                .build();

        System.out.println(uri);
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return 0;
        }

        String text = response.body();
        Scanner sc = new Scanner(text);
        if (sc.hasNextLine()) {
            sc.nextLine();
            while (sc.hasNextLine()) {
                String[] info = sc.nextLine().split("\\|");
                if (info[12].equals("CROATIA")) {
                    String[] dateAndTime = info[1].split("T");
                    if (dateDatabase.equals(dateAndTime[0])) {
                        System.out.println(info);
                        System.out.println(info[10]);
                        String magnitudeString = info[10];
                        sc.close();
                        return Float.parseFloat(magnitudeString);

                    }
                }
            }
        }
        sc.close();
        return 0;
    }

    public static void setMagnituda(int idPotres, float magnituda) {
        Database db = Database.getInstance();
        String sql = "UPDATE potres SET magnituda=? WHERE idPotres = ?";
        try (Connection connection = db.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setFloat(1, magnituda);
            pstmt.setInt(2, idPotres);
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

    public static void setIntenzitet(int idPotres, int intenzitet) {
        Database db = Database.getInstance();
        String sql = "UPDATE potres SET intenzitet=? WHERE idPotres = ?";
        try (Connection connection = db.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, intenzitet);
            pstmt.setInt(2, idPotres);
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
}
