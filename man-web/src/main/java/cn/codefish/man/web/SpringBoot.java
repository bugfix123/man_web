package cn.codefish.man.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.codefish.man.web.dao")
@SpringBootApplication
public class SpringBoot {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot.class, args);
	}

}
