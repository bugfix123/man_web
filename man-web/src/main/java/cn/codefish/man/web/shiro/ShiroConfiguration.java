package cn.codefish.man.web.shiro;

import java.util.HashMap;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@SpringBootConfiguration
public class ShiroConfiguration {
	@Bean
	public EhCacheManager ehCacheManager() {
		EhCacheManager em = new EhCacheManager();
		em.setCacheManagerConfigFile("classpath:config/shiro-ehcache.xml");
		return em;
	}

	@Bean
	public SystemShiroRealm systemShiroRealm(EhCacheManager ehCacheManager) {
		SystemShiroRealm ream = new SystemShiroRealm();
		ream.setCacheManager(ehCacheManager);
		return ream;
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
		daap.setProxyTargetClass(true);
		return daap;
	}

	@Bean
	public DefaultWebSecurityManager securityManager(SystemShiroRealm realm, EhCacheManager cacheManager) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager(realm);
		manager.setCacheManager(cacheManager);
		return manager;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager) {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(defaultWebSecurityManager);
		return aasa;
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(defaultWebSecurityManager);
		bean.setLoginUrl("/login");
		bean.setUnauthorizedUrl("/403");
		bean.setSuccessUrl("/main");
		HashMap<String, String> filterChainDefinitionMap = new HashMap<String, String>();

		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		// filterChainDefinitionMap.put("/bootstrap-3.3.7/**/**", "anon");
		// filterChainDefinitionMap.put("/bootstrap-table/**/**", "anon");
		// filterChainDefinitionMap.put("/admin/**/**", "anon");
		// filterChainDefinitionMap.put("/sweetalert/**/**", "anon");
		// filterChainDefinitionMap.put("/login/css/**", "anon");
		// filterChainDefinitionMap.put("/login/js/**", "anon");
		// filterChainDefinitionMap.put("/login/img/**/**", "anon");
		filterChainDefinitionMap.put("/3rd/**/**", "anon");
		filterChainDefinitionMap.put("/loginValidate", "anon");
		// filterChainDefinitionMap.put("/main", "authc,perms[user:edit]");
		filterChainDefinitionMap.put("/*", "authc");
		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return bean;

	}

	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

}