package cn.codefish.man.web.configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@SpringBootConfiguration 
public class WebConguration extends WebMvcConfigurerAdapter {

	// @Value("${server.context-path}")
	// private String contextPath;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration ir = registry.addInterceptor(new LoginInterceptor());
		ir.addPathPatterns("/**");
		ir.excludePathPatterns("error");
		ir.excludePathPatterns("/login**");
		ir.excludePathPatterns("/");
	}
}
