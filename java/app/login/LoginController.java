package app.login;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
import spark.Request;
import spark.Response;
import spark.Route;
import app.user.Uloga;
import app.user.User;
import app.user.UserController;
import app.user.UserDao;
import app.util.Path;
import app.util.ViewUtil;

public class LoginController {

	public static Route getLoginPage = (Request req, Response res) -> {
		Map<String, Object> model = new HashMap<>();
		model.put("title", "Login");
		return ViewUtil.render(model, Path.Templates.LOGIN);
	};

	
	public static Route handleLoginPost = (Request req, Response res) -> {
		String email = req.queryParams("username");
		String lozinka = req.queryParams("password");
		String error = UserController.checkLogin(email, lozinka);
		if(error == null) {
			User user = UserDao.getUserByEmail(email);
			req.session().attribute("user", user);
			if(user.getUloga() == Uloga.ADMIN) {
				res.redirect(Path.Web.STARIJI_POTRESI_ADMIN);
				return halt();
			}
			if(user.isPrva_prijava()){
				Map<String, Object> model = new HashMap<>();
				error = "Prva prijava, potrebno je postaviti vlastitu lozinku";
				model.put("error", error);
				return ViewUtil.render(model, Path.Templates.UPDATE_PASSWORD);
			}

			res.redirect(Path.Web.SVI_POTRESI);
			return halt();
		} else {
			Map<String, Object> model = new HashMap<>();
			model.put("error", error);
			model.put("title", "Login");
			return ViewUtil.render(model, Path.Templates.LOGIN);	
		}
	};

	public static Route handleLogout = (Request req, Response res) -> {
		req.session().removeAttribute("user");
		req.session().invalidate();
		res.redirect(Path.Web.INDEX);
		return null;
	};

	public static Route getChangePage = (Request req, Response res) -> {
		return ViewUtil.render(null, Path.Templates.UPDATE_PASSWORD);
	};


	

}
