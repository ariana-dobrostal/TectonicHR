package app.util;

import java.util.Map;

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class ViewUtil {
	public static String render(Map<String, Object> model, String fmTemplate) {
		return freemarkerEngine().render(new ModelAndView(model, fmTemplate));
	}

	@SuppressWarnings("deprecation")
	private static FreeMarkerEngine freemarkerEngine() {
		FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
		Configuration freeMarkerConfiguration = new Configuration(Configuration.VERSION_2_3_25);
		freeMarkerConfiguration.setClassForTemplateLoading(Path.Templates.class, "/");
		freeMarkerConfiguration.setOutputEncoding("utf-8");
		freeMarkerConfiguration.setDefaultEncoding("utf-8");
		freeMarkerEngine.setConfiguration(freeMarkerConfiguration);
		return freeMarkerEngine;
	}
}
