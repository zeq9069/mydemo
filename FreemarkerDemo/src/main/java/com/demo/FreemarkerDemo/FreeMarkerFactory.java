package com.demo.FreemarkerDemo;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * freemarker模板引擎
 * @author kyrin
 *
 */
public class FreeMarkerFactory {
	private static Configuration conf = new Configuration(Configuration.VERSION_2_3_21);

	public static Configuration init() {
		try {
			conf.setDirectoryForTemplateLoading(new File("src/main/java/com/demo/FreemarkerDemo/store/templates"));
			conf.setDefaultEncoding("UTF-8");
			conf.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conf;
	}

	public static Template getTemplate(String ftl) {
		init();
		Template template = null;
		try {
			template = conf.getTemplate(ftl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return template;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String s[]) {
		/* Get the template (uses cache internally) */
		Template template = getTemplate("test.ftl");

		/* ------------------------------------------------------------------------ */
		/* You usually do these for MULTIPLE TIMES in the application life-cycle:   */

		/* Create a data-model */
		Map root = new HashMap();
		root.put("user", "Big Joe");
		Map latest = new HashMap();
		root.put("latestProduct", latest);
		latest.put("url", "products/greenmouse.html");
		latest.put("name", "green mouse");

		/* Merge data-model with template */
		Writer out = new OutputStreamWriter(System.out);
		try {
			template.process(root, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Note: Depending on what `out` is, you may need to call `out.close()`.
		// This is usually the case for file output, but not for servlet output.
	}
}
