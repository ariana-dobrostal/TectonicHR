package app.potres;


import app.upitnik.Upitnik;
import app.upitnik.UpitnikDao;
import app.user.User;
import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PotresController {

    public static Route getAllEarthquakes = (Request req, Response res) -> {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Potres> potresi = PotresDao.getAllEarthquakes();
        map.put("title", "Potresi");
        map.put("potresi", potresi);
        return ViewUtil.render(map, Path.Templates.ALL_EARTHQUAKES);
    };

    public static Route getOldEarthquakesAdmin = (Request req, Response res) -> {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Potres> potresi = PotresDao.getOldEarthquakes();
        map.put("title", "Stari potresi");
        map.put("potresi", potresi);
        return ViewUtil.render(map, Path.Templates.OLD_EARTHQUAKES_ADMIN);
    };

    public static Route getRecentEarthquakesAdmin = (Request req, Response res) -> {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Potres> potresi = PotresDao.getRecentEarthquakes();
        map.put("title", "Novi potresi");
        map.put("potresi", potresi);
        return ViewUtil.render(map, Path.Templates.RECENT_EARTHQUAKES_ADMIN);
    };

    public static Route getRecentEarthquakes = (Request req, Response res) -> {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Potres> potresi = PotresDao.getRecentEarthquakes();
        map.put("title", "Novi potresi");
        map.put("potresi", potresi);
        return ViewUtil.render(map, Path.Templates.RECENT_EARTHQUAKES);
    };

    public static Route getOldEarthquakes = (Request req, Response res) -> {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Potres> potresi = PotresDao.getOldEarthquakes();
        map.put("title", "Stari potresi");
        map.put("potresi", potresi);
        return ViewUtil.render(map, Path.Templates.OLD_EARTHQUAKES);
    };

    public static Route deleteOldEarthquake = (Request req, Response res) -> {
        String id = req.params("idPotres");
        Potres potres = PotresDao.getPotresById(Integer.parseInt(id));
        if(potres != null) {
            PotresDao.deleteEarthquake(id);
        }
        res.redirect(Path.Web.STARIJI_POTRESI_ADMIN);
        return null;
    };

    public static Route deleteRecentEarthquake = (Request req, Response res) -> {
        String id = req.params("idPotres");
        Potres potres = PotresDao.getPotresById(Integer.parseInt(id));
        if(potres != null) {
            PotresDao.deleteEarthquake(id);
        }
        res.redirect(Path.Web.ZADNJI_POTRESI_ADMIN);
        return null;
    };

    public static Route updateEarthquake = (Request req, Response res) -> {
        String id = req.params("idPotres");
        PotresDao.updateEarthquake(id);
        res.redirect(Path.Web.ZADNJI_POTRESI_ADMIN);
        return null;
    };

    public static Route getEarthquakePage = (Request req, Response res) -> {
        int idPotres = Integer.parseInt(req.params("idPotres"));
        Potres potres = PotresDao.getPotresById(idPotres);
        ArrayList<Upitnik> upitnici = UpitnikDao.getUpitniciPotres(req.params("idPotres"));
        if(potres == null) {
            User user = req.session().attribute("user");
            if (!(user.isAdmin())) {
                res.redirect(Path.Web.SVI_POTRESI);
            }
            res.redirect(Path.Web.ZADNJI_POTRESI_ADMIN);
            return null;
        }
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Potres"); 
        model.put("potres", potres);
        model.put("upitnici", upitnici);
        return ViewUtil.render(model, Path.Templates.EARTHQUAKE);
    };
    public static Route getEarthquakePageAdmin = (Request req, Response res) -> {
        int idPotres = Integer.parseInt(req.params("idPotres"));
        Potres potres = PotresDao.getPotresById(idPotres);
        if(potres == null) {
            res.redirect(Path.Web.ZADNJI_POTRESI_ADMIN);
            return null;
        }
        ArrayList<Upitnik> upitnici = UpitnikDao.getUpitniciPotres(req.params("idPotres"));
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Potres"); 
        model.put("potres", potres);
        model.put("upitnici", upitnici);
        return ViewUtil.render(model, Path.Templates.EARTHQUAKE_ADMIN);
    };


    public static Route showMapEarthquake = (Request req, Response res) -> {
        String id = req.params("idPotres");
        Map<String, Object> model = new HashMap<>();
        Map<String, Float> epicenterCoords = PotresDao.calculateCoordsEpicenter(id);
        Map<String, Float> cityIntensity = PotresDao.getCityAndIntensity(id);
        String nazivPotres = PotresDao.getNazivPotres(id);
        Potres potres = PotresDao.getPotresById(Integer.parseInt(id));
        if(potres == null) {
            res.redirect(Path.Web.SVI_POTRESI);
            return null;
        }
        String date = PotresDao.getDate(id);
        model.put("title", "Karta");
        model.put("potres", potres);
        model.put("nazivPotres", nazivPotres );
        model.put("epicenter", epicenterCoords);
        model.put("cityIntensity", cityIntensity);
        model.put("date", date);
        return ViewUtil.render(model, Path.Templates.MAP);
    };
}
