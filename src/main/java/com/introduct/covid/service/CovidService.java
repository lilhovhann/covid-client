package com.introduct.covid.service;

import com.introduct.covid.interfaces.CovidServiceInterface;
import com.introduct.covid.CovidApplication;
import com.introduct.covid.domain.CovidData;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Lilit_Hovhannisyan
 */
@Service
@Slf4j
public class CovidService implements CovidServiceInterface {

    private final String baseUrl = "https://covid-api.mmediagroup.fr/v1";

    @Override
    public Optional<CovidData> getStatistics(String country) {

        country = formatString(country);
        
        final String url = baseUrl + "/cases?country=" + country;
        JSONObject result = (JSONObject) fetch(url, country).get("All");

        if (result != null) {
            return Optional.of(new CovidData(
                    (String) result.get("country"),
                    (Long) result.get("population"),
                    (Long) result.get("confirmed"),
                    (Long) result.get("recovered"),
                    (Long) result.get("deaths"),
                    getVaccinationInfo(country),
                    getNewConfirmedCases(country).get()));
        }

        return Optional.empty();
    }

    @Override
    public String getVaccinationInfo(String country) {
        
        country = formatString(country);
        
        final String url = baseUrl + "/vaccines?country=" + country;

        JSONObject result = (JSONObject) fetch(url, country).get("Global");
        JSONObject nestedResult = (JSONObject) result.get("All");
        //get first
        Long people_vaccinated = (Long) nestedResult.get("people_vaccinated");
        Long population = (Long) nestedResult.get("population");

        return String.valueOf(people_vaccinated * 100 / population);

    }
    
    @Override
    public Optional<Long> getNewConfirmedCases(String country){
        country = formatString(country);
        final String url = baseUrl + "/history?country=" + country+"&status=confirmed";
        JSONObject result = (JSONObject) fetch(url, country).get("All");
        JSONObject nestedResult = (JSONObject) result.get("dates");
        return nestedResult.values().stream().findFirst();
       
    }

    public JSONObject fetch(String url, String country) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        JSONParser parser = new JSONParser();
        JSONObject parsedData = new JSONObject();

        try {
            parsedData = (JSONObject) parser.parse(result);
        } catch (ParseException ex) {
            Logger.getLogger(CovidApplication.class.getName()).log(Level.SEVERE, null, ex);
        }

        return parsedData;
    }
    
    protected String formatString(String input){
        char firstLetter = Character.toUpperCase(input.charAt(0));
        return firstLetter + input.substring(1, input.length()).toLowerCase();
    }

}
