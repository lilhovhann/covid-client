package com.introduct.covid.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author Lilit_Hovhannisyan
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CovidData {
    
    private String country;
    
    private Long population;
    
    private Long confirmed;
    
    private Long recovered;
    
    private Long deaths;
    
    private String vaccinationLevel;
    
    private Long newConfirmed;
    
    @Override
    public String toString() { 
        return "Population: " + this.getPopulation() + "\n"+
               "Confirmed cases: " + this.getConfirmed()+ "\n"+
               "Recovered: " + this.getRecovered()+ "\n"+
               "Deaths: " + this.getDeaths()+ "\n"+
               "New confirmed cases: " + this.getNewConfirmed();
    }
    
}
