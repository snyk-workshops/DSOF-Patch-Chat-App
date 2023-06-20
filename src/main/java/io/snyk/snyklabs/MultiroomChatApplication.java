package io.snyk.snyklabs;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MultiroomChatApplication {

	@Value("#{ @environment['cors.allowed_domains']}")
	private String[] corsAllowedDomains;

	public static void main(String[] args) {
		SpringApplication.run(MultiroomChatApplication.class, args);
	}

	@Bean
	public ObjectMapper jacksonBuilder() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.registerModule(new VavrModule());
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/ws/**").allowedOrigins(corsAllowedDomains);
			}
		};
	}

}
