package com.demo.FreemarkerDemo;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.demo.FreemarkerDemo.entities.LatestProduct;
import com.demo.FreemarkerDemo.entities.Root;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.core.Environment;
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

	/* init config*/
	public static Configuration init() {
		try {
			conf.setDirectoryForTemplateLoading(new File("src/main/java/com/demo/FreemarkerDemo/store/templates"));
			conf.setDefaultEncoding("UTF-8");
			conf.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			//conf.setNumberFormat("0.##");
			//conf.setLocale(java.util.Locale.CHINA);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conf;
	}

	/* get Template*/
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

	/* test1:java collections as model */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void test1() {
		Template template = getTemplate("test_1.ftl");

		Map root = new HashMap();
		root.put("user", "Big Joe");
		Map latest = new HashMap();
		root.put("latestProduct", latest);
		latest.put("url", "products/greenmouse.html");
		latest.put("name", 0.111111);

		Writer out = new OutputStreamWriter(System.out);
		try {
			template.process(root, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* test1: javaBean as model */
	public static void test1_1() {
		Template template = getTemplate("test_1.ftl");
		Root root = new Root();
		root.setUser("Kyrin");
		LatestProduct latestProduct = new LatestProduct();
		latestProduct.setName("Heier");
		latestProduct.setUrl("http://baidu.com");
		root.setLatestProduct(latestProduct);

		Writer out = new OutputStreamWriter(System.out);

		try {
			template.process(root, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* test1: envorinment setting */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void test1_3() {
		Template template = getTemplate("test_1.ftl");
		Map root = new HashMap();
		root.put("user", "小明");
		Map latest = new HashMap();
		root.put("latestProduct", latest);
		latest.put("url", "products/greenmouse.html");
		latest.put("name", "Jack");

		Writer out = new OutputStreamWriter(System.out);

		try {
			Environment env = template.createProcessingEnvironment(root, out);
			env.setDateFormat("yyyy-mm-ss");
			env.setLocale(java.util.Locale.CHINA);
			env.setOutputEncoding("UTF-8");
			env.process();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*Loading templates from multiple locations */
	public void loadingTemoplatesFromMultiLocations() throws IOException {
		FileTemplateLoader ftl1 = new FileTemplateLoader(new File("/tmp1/templates"));
		FileTemplateLoader ftl2 = new FileTemplateLoader(new File("/tmp2/templates"));
		ClassTemplateLoader ftl3 = new ClassTemplateLoader(getClass(), "");
		TemplateLoader[] loaders = new TemplateLoader[] { ftl1, ftl2, ftl3 };
		MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
		conf.setTemplateLoader(mtl);
	}

	public static void main(String s[]) {
		test1();
		test1_1();
		test1_3();
	}
}
