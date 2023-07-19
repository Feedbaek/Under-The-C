package UnderTheC.DeepSea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DeepSeaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeepSeaApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedOrigins("http://" + System.getenv("REACT_HOST") + ":" + System.getenv("REACT_PORT"), "http://localhost:" + System.getenv("REACT_PORT"))
				.allowedMethods("*")
				.allowCredentials(true);
			}
		};
	}
}
