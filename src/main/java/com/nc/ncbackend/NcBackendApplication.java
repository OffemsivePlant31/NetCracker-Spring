package com.nc.ncbackend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nc.ncbackend.converter.gson.LocalDateAdapter;
import com.nc.ncbackend.converter.gson.LocalDateTimeAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = NcBackendApplication.class)
@ComponentScan("com.nc.ncbackend")
@EnableScheduling
public class NcBackendApplication extends WebMvcConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(NcBackendApplication.class, args);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter< ? >> converters) {
		GsonHttpMessageConverter msgConverter = new GsonHttpMessageConverter();
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				.create();
		msgConverter.setGson(gson);
		converters.add(msgConverter);

	}
}
