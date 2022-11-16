/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.introduct.covid.interfaces;

import com.introduct.covid.domain.CovidData;
import java.util.Optional;

/**
 *
 * @author Lilit_Hovhannisyan
 */
public interface CovidServiceInterface {
    
    public Optional<CovidData> getStatistics(String country);
    
    public String getVaccinationInfo(String country);
    
    public Optional<Long> getNewConfirmedCases(String country);
}
