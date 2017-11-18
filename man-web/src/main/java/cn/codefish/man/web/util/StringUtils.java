package cn.codefish.man.web.util;

import java.util.UUID;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static void main(String[] args) {
		System.out.println(getUUID());
	}
}
