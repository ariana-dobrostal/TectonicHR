package app.user;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.HashMap;

import app.util.ViewUtil;
import app.util.Path;

import java.util.Map;
import java.util.List;
import java.io.OutputStream;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.File;

import app.potres.Potres;
import app.potres.PotresDao;
import app.upitnik.UpitnikDao;

public class UserController {
    public static String checkLogin(String username, String pwd) {
        String error = null;
        if (username.equals("") || pwd.equals("")) {
            error = "Please input all fields";
        } else {
            User user = UserDao.getUserByEmail(username);
            if (user == null) {
                error = "Wrong username";
            } else if (!user.checkPassword(pwd)) {
                error = "Wrong password";
            }
        }
        return error;
    }

    public static Route getUsersPage = (Request req, Response res) -> {
        HashMap<String, Object> model = new HashMap<>();

        ArrayList<User> users = UserDao.getUsers();
        if (users.size() > 0) {
            model.put("users", users);
        }
        model.put("title", "Korisnici");

        return ViewUtil.render(model, Path.Templates.USERS);
    };
    // dowload se pokreće dodavanjem gumba u točno ovakvom formatu
    // <a href="/download/${upitnik.getIdUpitnik()}" download="podaci.txt">Skini
    // podatke</a>
    // i sprema se pod imenom podaci.txt na računalo

    public static Route downloadDataUpitnici = (Request req, Response res) -> {
        String id = req.params("idPotres");
        FileWriter writer = new FileWriter("upitnici.txt");
        List<String> lista;
        lista = UpitnikDao.getUpitniciInfo(id);
        writer.write("Ispunjeni upitnici za potres su:\n");
        for (String s : lista) {
            writer.write(s);
            writer.write("\n");
        }
        writer.close();
        File file = new File("upitnici.txt");
        OutputStream outputStream = res.raw().getOutputStream();
        outputStream.write(Files.readAllBytes(file.toPath()));
        outputStream.flush();
        return null;
    };

    public static Route downloadDataPotres = (Request req, Response res) -> {
        int idPotres = Integer.parseInt(req.params("idPotres"));
        FileWriter writer = new FileWriter("potres.txt");
        Potres potres = PotresDao.getPotresById(idPotres);
        Map<String, Float> city_intensity = PotresDao.getCityAndIntensity(req.params("idPotres"));
        Map<String, Float> coords = PotresDao.calculateCoordsEpicenter(req.params("idPotres"));
        writer.write("NAZIV POTRESA: " + potres.getNazivPotres() + "\n");
        writer.write("INTENZITET: " + potres.getIntenzitet() + "\n");
        writer.write("MAGNITUDA: " + potres.getMagnituda() + "\n");
        if (!Float.isNaN(coords.get("lat"))) {
            writer.write("KOORDINATE EPICENTRA: lat = " + coords.get("lat") + " long = " + coords.get("lng"));
        }
        writer.write("\n");
        for (Map.Entry<String, Float> entry : city_intensity.entrySet()) {
            String city = entry.getKey();
            float[] coords_c = PotresDao.getCoordinates(city);
            writer.write(
                    city + " lat = " + coords_c[1] + " long = " + coords_c[0] + " intenzitet : " + entry.getValue());
            writer.write("\n");
        }
        writer.write("\n");
        writer.close();
        File file = new File("potres.txt");
        OutputStream outputStream = res.raw().getOutputStream();
        outputStream.write(Files.readAllBytes(file.toPath()));
        outputStream.flush();
        return null;
    };

    public static Route changePassword = (Request req, Response res) -> {
        User user = req.session().attribute("user");
        String email = user.getEmail();
        String lozinka = req.queryParams("new_pass");
        String lozinka_potvrda = req.queryParams("confirm_pass");
        String error = UserController.checkChangedPassword(email, lozinka, lozinka_potvrda);
        if (error.equals("Lozinka promijenjena, prijavite se s novim podacima")) {
            UserDao.updatePassword(email, lozinka);
        } else {
            Map<String, Object> model = new HashMap<>();
            model.put("error", error);
            model.put("title", "Promijena lozinke");
            return ViewUtil.render(model, Path.Templates.UPDATE_PASSWORD);
        }
        Map<String, Object> model = new HashMap<>();
        model.put("error", error);
        model.put("title", "Prijava");
        return ViewUtil.render(model, Path.Templates.LOGIN);

    };

    public static Route doNotChangePassword = (Request req, Response res) -> {
        User user = req.session().attribute("user");
        UserDao.setFirstLogin(user.getEmail());
        res.redirect(Path.Web.SVI_POTRESI);
        return null;
    };

    public static String checkChangedPassword(String username, String newPass, String confirmPass) {
        String error = "Lozinka promijenjena, prijavite se s novim podacima";
        if (newPass.equals("") || confirmPass.equals("")) {
            error = "Sva polja su obavezna";
        } else if (!newPass.equals(confirmPass)) {
            error = "Obje lozinke moraju biti jednake";
        }

        return error;
    }
}
