package com.douzone.surveymanagement;

import java.io.IOException;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ResourceUtils;


@EnableScheduling
@SpringBootApplication
public class SurveyManagementBackendApplication implements CommandLineRunner {

	@Autowired
	private Environment environment;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SurveyManagementBackendApplication.class, args);

		System.out.println("application-prod.yml is loaded: " + ResourceUtils.isUrl("classpath:application-prod.yml"));
		System.out.println("Mapper XML files: " + Arrays.toString(ResourcePatternUtils.getResourcePatternResolver(new PathMatchingResourcePatternResolver()).getResources("classpath:mapper/*.xml")));
	}

	@Override
	public void run(String... args) {
		System.out.println(environment.getProperty("spring.datasource.url"));
		System.out.println(environment.getProperty("spring.datasource.username"));
		System.out.println(environment.getProperty("spring.datasource.password"));
	}

}
