package com.introduct.covid;

import com.introduct.covid.domain.CovidData;
import com.introduct.covid.service.CovidService;
import java.time.Duration;
import java.util.Optional;
import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CovidApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(CovidApplication.class, args);
        CovidService covidService = applicationContext.getBean(CovidService.class);

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("----Welcome to 'COVID STATISTICS'----");
        System.out.println("=================================================================================");
        System.out.println("Please enter the country name (example: France)");

        String country = myObj.nextLine();  // Read user input
        System.out.println("Counting the COVID statistics of " + country + "...\n");
        Optional<CovidData> covidData = covidService.getStatistics(country);

        if (covidData.isPresent()) {
            System.out.println(covidData.get().toString());
            System.out.println("Vaccination level: "+covidData.get().getVaccinationLevel()+"%");
        }else{
             System.out.println("The company name is not valid");
        }
        

        

    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .build();
    }

}
