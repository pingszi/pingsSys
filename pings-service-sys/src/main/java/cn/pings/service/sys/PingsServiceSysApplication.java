package cn.pings.service.sys;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class PingsServiceSysApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(PingsServiceSysApplication.class)
				.web(WebApplicationType.NONE) //**非Web应用
				.run(args);
	}

}

