package app.admin;

import spark.Response;
import spark.Request;
import spark.Route;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import app.util.Path;
import app.util.ViewUtil;
import app.potres.*;
import app.upitnik.UpitnikDao;
import app.user.*;

public class AdminController {
    public static Route getRegistrationPage = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Registracija");
        return ViewUtil.render(model, Path.Templates.REGISTER);
    };

    public static Route handleRegistrationPost = (Request req, Response res) -> {
        String ime = req.queryParams("ime");
        String prezime = req.queryParams("prezime");
        String email = req.queryParams("username");
        String lozinka = req.queryParams("password");
        User user = new User(ime, prezime, email, lozinka, Uloga.ZNANSTVENIK, true);
        boolean registered = UserDao.addUser(user);
        if (!registered) {
            HashMap<String, Object> model = new HashMap<>();
            model.put("title", "Registracija");
            model.put("registrationFailed", true);
            return ViewUtil.render(model, Path.Templates.REGISTER);
        }
        res.redirect(Path.Web.KORISNICI);
        return null;
    };

    public static Route deleteUser = (Request req, Response res) -> {
        String username = req.params("username");
        User user = UserDao.getUserByEmail(username);
        if (user != null) {
            UserDao.deleteUser(username);
        }
        res.redirect(Path.Web.KORISNICI);
        return null;
    };

    public static Route deleteUpitnik = (Request req, Response res) -> {
        String idUpitnik = req.params("idUpitnik");
        if (!UpitnikDao.upitnikExists(idUpitnik)) {
            res.redirect(Path.Web.NOVI_UPITNICI);
            return null;
        }
        UpitnikDao.deleteUpitnik(idUpitnik);
        res.redirect(Path.Web.NOVI_UPITNICI);
        return null;
    };

    public static Route deleteUpitnikPotres = (Request req, Response res) -> {
        String idUpitnik = req.params("idUpitnik");
        String idPotres = req.params("idPotres");
        if (!UpitnikDao.upitnikExists(idUpitnik)) {
            res.redirect(Path.Web.NOVI_UPITNICI);
            return null;
        }
        UpitnikDao.deleteUpitnik(idUpitnik);
        PotresDao.updateEarthquakeParams(Integer.parseInt(idPotres));
        res.redirect("/potres_admin/" + idPotres);
        return null;
    };

    public static Route getAddUpitnikToPotres = (Request req, Response res) -> {
        String upitnikId = req.params("idUpitnik");
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Dodijeli upitnik");
        model.put("upitnikId", upitnikId);
        model.put("potresi", PotresDao.getAllEarthquakes());
        return ViewUtil.render(model, Path.Templates.ALL_EARTHQUAKES_ADMIN);
    };

    public static Route addUpitnikToPotres = (Request req, Response res) -> {
        ArrayList<String> upitnikLista = new ArrayList<>();
        String upitnikId = req.params("idUpitnik");
        upitnikLista.add(upitnikId);
        int potresId = Integer.parseInt(req.queryParams("potresId"));
        UpitnikDao.putUpitnikPotres(upitnikLista, potresId);
        PotresDao.updateEarthquakeParams(potresId);
        res.redirect(Path.Web.NOVI_UPITNICI);
        return null;
    };

    public static Route addUpitnikToDrugiPotres = (Request req, Response res) -> {
        ArrayList<String> upitnikLista = new ArrayList<>();
        String upitnikId = req.params("idUpitnik");
        String idPotres = req.params("idPotres");
        upitnikLista.add(upitnikId);
        int potresId = Integer.parseInt(req.queryParams("potresId"));
        UpitnikDao.putUpitnikPotres(upitnikLista, potresId);
        PotresDao.updateEarthquakeParams(potresId);
        PotresDao.updateEarthquakeParams(Integer.parseInt(idPotres));
        res.redirect("/potres_admin/" + idPotres);
        return null;
    };

    public static Route getNewEarthquakePage = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Novi potres");
        model.put("upitnici", UpitnikDao.getNoviUpitnici());
        return ViewUtil.render(model, Path.Templates.NEW_EARTHQUAKE);
    };

    public static Route makePotres = (Request req, Response res) -> {
        String naziv = req.queryParams("naziv");
        String vrijeme = req.queryParams("vrijeme");
        String datum = req.queryParams("datum");
        int novi = 1;
        Potres potres = new Potres(null, 0, 0, naziv, datum, vrijeme, novi);
        Integer idPotres = PotresDao.addEarthquake(potres);
        ArrayList<String> upitnikLista = new ArrayList<>();
        for (String param : req.queryParams()) {
            try {
                Integer upitnikId = Integer.parseInt(param);
                upitnikLista.add(upitnikId.toString());
            } catch (NumberFormatException ignored) {
            }
        }

        if (!upitnikLista.isEmpty()) {
            UpitnikDao.putUpitnikPotres(upitnikLista, idPotres);
            PotresDao.updateEarthquakeParams(idPotres);
        }

        int intenzitet = PotresDao.calculateIntenzitet(idPotres.toString());
        float magnituda = PotresDao.getMagnituda(idPotres.toString());
        PotresDao.setIntenzitet(idPotres, intenzitet);
        PotresDao.setMagnituda(idPotres, magnituda);

        res.redirect(Path.Web.ZADNJI_POTRESI_ADMIN);
        return null;
    };
}
