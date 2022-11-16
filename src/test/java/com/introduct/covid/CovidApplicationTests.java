package com.introduct.covid;

import com.introduct.covid.service.CovidService;
import org.json.simple.JSONObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CovidApplicationTests {

    @Spy
    @InjectMocks
    private CovidService covidServiceMock;

    private static final String URL = "https://covid-api.mmediagroup.fr/v1/vaccines?country=France";
    private static final String URL2 = "https://covid-api.mmediagroup.fr/v1/history?country=France&status=confirmed";

    @Test
    void shouldReturnCovidVaccinationPersentage() {

        // GIVEN
        String country = "France";
        String vaccinated = "0";

        JSONObject json = new JSONObject();
        JSONObject item = new JSONObject();
        JSONObject nestedItem = new JSONObject();
        json.put("Global", item);

        item.put("All", nestedItem);

        nestedItem.put("population", 7444509223L);
        nestedItem.put("administered", 0L);
        nestedItem.put("people_vaccinated", 0L);
        nestedItem.put("people_partially_vaccinated", 0L);

        when(covidServiceMock.fetch(URL, country)).thenReturn(json);

        // WHEN
        String actualResult = covidServiceMock.getVaccinationInfo(country);

        // THEN
        String expectedResult = vaccinated;

        assertEquals(expectedResult, actualResult);

    }

    @Test
    void shouldReturnCovidNewConfirmedCases() {

        // GIVEN
        String country = "France";
        Long newConfirmed = 36193559L;

        JSONObject json = new JSONObject();
        JSONObject item = new JSONObject();
        JSONObject nestedItem = new JSONObject();
        json.put("All", item);

        item.put("population", 7444509223L);
        item.put("dates", nestedItem);

        nestedItem.put("2022-11-15", 36193559L);
        nestedItem.put("2022-11-14", 3614522L);

        when(covidServiceMock.fetch(URL2, country)).thenReturn(json);

        // WHEN
        Long actualResult = covidServiceMock.getNewConfirmedCases(country).get();

        // THEN
        Long expectedResult = newConfirmed;

        assertEquals(expectedResult, actualResult);

    }
}
