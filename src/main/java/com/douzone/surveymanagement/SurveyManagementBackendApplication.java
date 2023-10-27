package com.douzone.surveymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class SurveyManagementBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveyManagementBackendApplication.class, args);
	}

}
