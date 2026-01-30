package com.banco.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * COnfiguracion global de CORS
 * Permite que el frontend (Angular) consuma la API sin errores de CORS en el navegador.
 * 
 * Se cofigura a nivel global para evitar repetir @CrossOrigin en cada controller.
 */

@Configuration
public class CorsConfig {
	/**
	 * Define las reglas globales de CORS para la aplicacion
	 * @return
	 */
	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200", "http://127.0.0.1:4200")
						.allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
						.allowedHeaders("*");
			}
		};
	}
}
