package cn.pings.service.bill;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class PingsServiceBillApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(PingsServiceBillApplication.class)
				.web(WebApplicationType.NONE) //**非Web应用
				.run(args);
	}

}
