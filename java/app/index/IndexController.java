package app.index;

import java.util.HashMap;
import java.util.Map;

import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class IndexController {
	public static Route getIndexPage = (Request req, Response res)-> {
		Map<String, Object> model = new HashMap<>();
		model.put("title", "TectonicHR");
		return ViewUtil.render(model, Path.Templates.INDEX_PAGE);
	};
}
