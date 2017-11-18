package cn.codefish.man.web.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class SQLLoader {
	public static String getSql(String fileName, String key) {
		String cp = "sql" + "/" + fileName;
		String sql = "";
		try {
			Properties p = PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource(cp)));
			sql = p.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sql;

	}
}
