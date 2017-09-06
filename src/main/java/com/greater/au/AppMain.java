package com.greater.au;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * AppMain class used for running the job
 * 
 * 
 * @author neel
 *
 */
@SpringBootApplication
@EnableScheduling
@ImportResource({"classpath*:applicationContext.xml"})
public class AppMain {

	public static void main(String[] args) {
		 SpringApplication.run(AppMain.class);
	}

}
