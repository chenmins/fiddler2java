package im.dpm.tools;

import java.io.FileWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class Templete {

	private static Properties props = new Properties();

	static {
		props.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		props.setProperty(Velocity.RESOURCE_LOADER, "class");
		props
				.setProperty("class.resource.loader.class",
						"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	}

	public static String tempFile(String file, Map<String, Object> data)
			throws Exception {
		VelocityEngine engine = new VelocityEngine(props);
		engine.init();
		// 取得velocity的上下文context
		VelocityContext context = new VelocityContext();
		for (String key : data.keySet()) {
			context.put(key, data.get(key));
		}
		StringWriter writer = new StringWriter();
		engine.mergeTemplate(file, "UTF-8", context, writer);
		return writer.toString();
	}
	
	public static void tempFileToFile(String file, Map<String, Object> data,String toFile)
			throws Exception {
		VelocityEngine engine = new VelocityEngine(props);
		engine.init();
		// 取得velocity的上下文context
		VelocityContext context = new VelocityContext();
		for (String key : data.keySet()) {
			context.put(key, data.get(key));
		}
		FileWriter writer = new FileWriter(toFile);
		engine.mergeTemplate(file, "UTF-8", context, writer);
		writer.close();
	}

	public static String temp(String format, Map<String, Object> data)
			throws Exception {
		VelocityEngine engine = new VelocityEngine();
		engine.init();
		// 取得velocity的上下文context
		VelocityContext context = new VelocityContext();
		// String key = data.keySet().iterator()
		for (String key : data.keySet()) {
			context.put(key, data.get(key));
		}
		StringWriter writer = new StringWriter();
		engine.evaluate(context, writer, "debug", format);
		return writer.toString();
	}

	public static void main(String[] args) throws Exception {
		// VelocityEngine engine = new VelocityEngine();
		// engine.init();
		// // 字符串模版
		// String template = "${owner}：您的${type} : ${bill} 在 ${date} 日已支付成功";
		// // 取得velocity的上下文context
		// VelocityContext context = new VelocityContext();
		// // 把数据填入上下文
		// context.put("owner", "nassir");
		// context.put("bill", "201203221000029763");
		// context.put("type", "订单");
		// context.put("date",
		// new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		// StringWriter writer = new StringWriter();
		// engine.evaluate(context, writer, "debug", template);
		// System.out.println(writer.toString());

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("owner", "nassir");
		m.put("bill", "201203221000029763");
		m.put("type", "订单");
		m.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date()));
		String template = "${owner}：您的${type} : ${bill} 在  ${date} 日已支付成功";

		System.out.println(Templete.temp(template, m));

		System.out.println(Templete.tempFile("order.vm", m));
	}
}
