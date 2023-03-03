package app.upitnik;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Collections;

import app.pitanje.Pitanje;
import app.pitanje.PitanjeDao;
import app.potres.Potres;
import app.potres.PotresDao;
import app.user.User;
import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class UpitnikController {
    public static Route getNewFormsAdmin = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        ArrayList<Upitnik> upitnici = UpitnikDao.getNoviUpitnici();
        model.put("title", "Novi upitnici");
        model.put("upitnici", upitnici);
        return ViewUtil.render(model, Path.Templates.NEW_QUESTIONNAIRES);
    };

    public static Route getFormPage = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Upitnik");
        return null;
    };

    public static Route getUpitnik = (Request req, Response res) -> {
        String id = req.params("idUpitnik");
        if(!UpitnikDao.upitnikExists(id)) {
            User user = req.session().attribute("user");
            if (!(user.isAdmin())) {
                res.redirect(Path.Web.SVI_POTRESI);
            }
            res.redirect(Path.Web.NOVI_UPITNICI);
            return null;
        }
        Map<String, Object> model = new HashMap<>();
        List<String> pitanjaIOdgovori = UpitnikDao.getPitanjaIOdgovori(Integer.parseInt(id));
        model.put("title", "Upitnik");
        model.put("pitanjaIOdgovori", pitanjaIOdgovori);
        return ViewUtil.render(model, Path.Templates.FILLED_QUESTIONNAIRE);
    };

    public static Route getNoviUpitnik = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        ArrayList<Pitanje> pitanja = PitanjeDao.getQuestions();
        ArrayList<String> mjesta = UpitnikDao.getMjesta();
        model.put("title", "Upitnik");
        model.put("pitanja", pitanja);
        model.put("mjesta", mjesta);
        return ViewUtil.render(model, Path.Templates.FILL_QUESTIONNAIRE);
    };

    public static Route getNoviUpitnikPotres = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        ArrayList<Pitanje> pitanja = PitanjeDao.getQuestions();
        Potres potres = PotresDao.getPotresById(Integer.parseInt(req.params("idPotres")));
        if(potres == null) {
            res.redirect(Path.Web.ZADNJI_POTRESI);
            return null;
        }
        ArrayList<String> mjesta = UpitnikDao.getMjesta();
        model.put("title", "Upitnik");
        model.put("potres", potres);
        model.put("pitanja", pitanja);
        model.put("mjesta", mjesta);
        return ViewUtil.render(model, Path.Templates.FILL_QUESTIONNAIRE);
    };

    public static Route fillNewUpitnik = (Request req, Response res) -> {
        String datum = req.queryParams("datum");
        String vrijeme = req.queryParams("vrijeme");
        vrijeme = vrijeme + ":00";
        String mjesto = req.queryParams("mjesto");
        ArrayList<Integer> intenziteti = new ArrayList<>();
        HashMap<Integer, Integer> pitanje_intenzitet = new HashMap<>();
        for (int i = 1; i < 11; i++) {
            int inten = Integer.parseInt(req.queryParams(String.valueOf(i)));
            intenziteti.add(inten);
            pitanje_intenzitet.put(i, inten);
        }
        int intenzitet = Collections.max(intenziteti);
        Upitnik upitnik = new Upitnik(0, intenzitet, datum, vrijeme, mjesto);
        int idUpitnik = UpitnikDao.addUpitnik(upitnik);
        UpitnikDao.addUpitnikData(pitanje_intenzitet, idUpitnik);

        Map<String, Object> model = new HashMap<>();
        model.put("title", "Obavijest");
        model.put("return", true);
        model.put("notification", "Uspješno ste ispunili upitnik.");

        return ViewUtil.render(model, Path.Templates.NOTIFICATION);
    };

    public static Route fillOldUpitnik = (Request req, Response res) -> {
        String idPotres = req.params("idPotres");
        Potres potres = PotresDao.getPotresById(Integer.parseInt(idPotres));
        String datum = potres.getDatum();
        String vrijeme = req.queryParams("vrijeme");
        vrijeme = vrijeme + ":00";
        String mjesto = req.queryParams("mjesto");
        ArrayList<Integer> intenziteti = new ArrayList<>();
        HashMap<Integer, Integer> pitanje_intenzitet = new HashMap<>();
        for (int i = 1; i < 11; i++) {
            int inten = Integer.parseInt(req.queryParams(String.valueOf(i)));
            intenziteti.add(inten);
            pitanje_intenzitet.put(i, inten);
        }
        int intenzitet = Collections.max(intenziteti);
        Upitnik upitnik = new Upitnik(0, intenzitet, datum, vrijeme,
                Integer.parseInt(idPotres), mjesto);
        int idUpitnik = UpitnikDao.addUpitnik(upitnik);
        UpitnikDao.addUpitnikData(pitanje_intenzitet, idUpitnik);
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Obavijest");
        model.put("return", true);
        model.put("notification", "Uspješno ste ispunili upitnik.");

        return ViewUtil.render(model, Path.Templates.NOTIFICATION);
    };

}
