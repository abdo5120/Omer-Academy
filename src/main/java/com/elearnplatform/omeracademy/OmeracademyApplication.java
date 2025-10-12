package com.elearnplatform.omeracademy;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "Omer Academy API",
				version = "1.0",
				description = "APIs for Omer Academy")
)
public class OmeracademyApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmeracademyApplication.class, args);
	}

}
