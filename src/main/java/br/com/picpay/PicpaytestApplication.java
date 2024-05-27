package br.com.picpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PicpaytestApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicpaytestApplication.class, args);
	}

}
