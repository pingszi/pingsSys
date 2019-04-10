package cn.pings.web.admin;

import cn.pings.service.api.util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PingsWebAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(PingsWebAdminApplication.class, args);
	}

	@Bean("springContextUtil")
	public SpringContextUtil SpringContextUtil() {
		return new SpringContextUtil();
	}

}

