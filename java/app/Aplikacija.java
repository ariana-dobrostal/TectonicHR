package app;

import static spark.Spark.*;

import app.db.Database;
import app.admin.AdminController;
import app.index.IndexController;
import app.login.LoginController;
import app.potres.PotresController;
import app.upitnik.UpitnikController;
import app.user.User;
import app.user.UserController;
import app.user.UserDao;
import app.util.Path;
import spark.Filter;
import spark.Request;
import spark.Response;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Aplikacija {

	private static Filter CHECK_USER_ADMIN = (Request req, Response res) -> {
		User user = req.session().attribute("user");
		if (user == null || !(user.isAdmin())) {
			res.redirect(Path.Web.PRIJAVA);
			halt();
		}
	};
	private static Filter CHECK_USER = (Request req, Response res) -> {
		User user = req.session().attribute("user");
		if (user == null) {
			res.redirect(Path.Web.PRIJAVA);
			halt();
		}
		else if(UserDao.getUserByEmail(user.getEmail()) == null) {
			res.redirect(Path.Web.PRIJAVA);
			halt();
		}
	};

	public static void mainExample(String[] args) {
		// Setup inside main
		// -----------------
		Config config = Config.fromCommandLine(args);
		Database mainDb = new Database(config.DB_HOST, config.DB_PORT, config.DB_DB, config.DB_USER, config.DB_PASS);
		Database.setInstance(mainDb);

		// Gdje treba Database
		Database db = Database.getInstance();
		try (Connection connection = db.connect()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM pitanje");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		Config config = Config.fromCommandLine(args);
		Database mainDb = new Database(config.DB_HOST, config.DB_PORT, config.DB_DB, config.DB_USER, config.DB_PASS);
		Database.setInstance(mainDb);
		port(4567);
		setupSparkRoutes();

	}

	public static void setupSparkRoutes() {
		staticFiles.location("/public");
		//staticFiles.expireTime(600L);

		before(Path.Web.KORISNICI, CHECK_USER_ADMIN);
		before(Path.Web.IZBRISI_KORISNIKA, CHECK_USER_ADMIN);
		before(Path.Web.IZBRISI_UPITNIK, CHECK_USER_ADMIN);
		before(Path.Web.IZBRISI_UPITNIK_POTRES, CHECK_USER_ADMIN);
		before(Path.Web.IZBRISI_STARI_POTRES, CHECK_USER_ADMIN);
		before(Path.Web.IZBRISI_ZADNJI_POTRES, CHECK_USER_ADMIN);
		before(Path.Web.POMAKNI_POTRES, CHECK_USER_ADMIN);
		before(Path.Web.REGISTRACIJA, CHECK_USER_ADMIN);
		before(Path.Web.NOVI_UPITNICI, CHECK_USER_ADMIN);
		before(Path.Web.STARIJI_POTRESI_ADMIN, CHECK_USER_ADMIN);
		before(Path.Web.ZADNJI_POTRESI_ADMIN, CHECK_USER_ADMIN);
		before(Path.Web.NOVI_POTRES, CHECK_USER_ADMIN);
		before(Path.Web.DODIJELI_UPITNIK, CHECK_USER_ADMIN);
		before(Path.Web.ISPUNJENI_UPITNIK, CHECK_USER_ADMIN);
		before(Path.Web.POTRES_ADMIN, CHECK_USER_ADMIN);

		before(Path.Web.SVI_POTRESI, CHECK_USER);
		before(Path.Web.POTRES, CHECK_USER);
		before(Path.Web.ISPUNJENI_UPITNIK, CHECK_USER);
		before(Path.Web.DOWNLOAD_EARTHQUAKE_INFO, CHECK_USER);
		before(Path.Web.DOWNLOAD_UPITNICI, CHECK_USER);
		before(Path.Web.BEZ_PROMJENE_LOZINKE, CHECK_USER);
		before(Path.Web.UPDATE_LOZINKU, CHECK_USER);


		get(Path.Web.INDEX, IndexController.getIndexPage);
		path(Path.Web.PRIJAVA, () -> {
			get("", LoginController.getLoginPage);
			post("", LoginController.handleLoginPost);
		});

		get(Path.Web.ODJAVA, LoginController.handleLogout);

		path(Path.Web.REGISTRACIJA, () -> {
			get("", AdminController.getRegistrationPage);
			post("", AdminController.handleRegistrationPost);
		});
		get(Path.Web.STARIJI_POTRESI_ADMIN, PotresController.getOldEarthquakesAdmin);
		get(Path.Web.ZADNJI_POTRESI_ADMIN, PotresController.getRecentEarthquakesAdmin);
		get(Path.Web.ZADNJI_POTRESI, PotresController.getRecentEarthquakes);
		get(Path.Web.SVI_POTRESI, PotresController.getAllEarthquakes);
		get(Path.Web.POTRES, PotresController.getEarthquakePage);
		get(Path.Web.POTRES_ADMIN, PotresController.getEarthquakePageAdmin);
		get(Path.Web.KORISNICI, UserController.getUsersPage);
		get(Path.Web.STARIJI_POTRESI, PotresController.getOldEarthquakes);
		get(Path.Web.NOVI_UPITNICI, UpitnikController.getNewFormsAdmin);
		get(Path.Web.IZBRISI_UPITNIK, AdminController.deleteUpitnik);
		get(Path.Web.IZBRISI_UPITNIK_POTRES, AdminController.deleteUpitnikPotres);
		get(Path.Web.IZBRISI_KORISNIKA, AdminController.deleteUser);
		get(Path.Web.IZBRISI_STARI_POTRES, PotresController.deleteOldEarthquake);
		get(Path.Web.IZBRISI_ZADNJI_POTRES, PotresController.deleteRecentEarthquake);
		get(Path.Web.POMAKNI_POTRES, PotresController.updateEarthquake);
		get(Path.Web.ISPUNJENI_UPITNIK, UpitnikController.getUpitnik);
		get(Path.Web.KARTA, PotresController.showMapEarthquake);
		get(Path.Web.DOWNLOAD_UPITNICI, UserController.downloadDataUpitnici);
		get(Path.Web.DOWNLOAD_EARTHQUAKE_INFO, UserController.downloadDataPotres);
		get(Path.Web.BEZ_PROMJENE_LOZINKE, UserController.doNotChangePassword);

		path(Path.Web.NOVI_POTRES, () -> {
			get("", AdminController.getNewEarthquakePage);
			post("", AdminController.makePotres);
		});
		path(Path.Web.DODIJELI_UPITNIK, () -> {
			get("", AdminController.getAddUpitnikToPotres);
			post("", AdminController.addUpitnikToPotres);
		});

		path(Path.Web.DODIJELI_UPITNIK_DRUGOM, () -> {
			get("", AdminController.getAddUpitnikToPotres);
			post("", AdminController.addUpitnikToDrugiPotres);
		});

		path(Path.Web.UPDATE_LOZINKU, () -> {
			get("", LoginController.getChangePage);
			post("", UserController.changePassword);
		});

		path(Path.Web.ISPUNI_UPITNIK, () -> {
			get("", UpitnikController.getNoviUpitnik);
			post("", UpitnikController.fillNewUpitnik);
		});

		path(Path.Web.ISPUNI_UPITNIK_ZA_POTRES, () -> {
			get("", UpitnikController.getNoviUpitnikPotres);
			post("", UpitnikController.fillOldUpitnik);
		});

		notFound((req, res) -> {
			res.redirect(Path.Web.INDEX);
			return null;
		});
	}
}
