package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)// if i didn't want all the code
public class CountryModel
{
    private String name ;
    private String subregion ;
    private String region ;
    private String capital ;
    private long population ;

    private String alpha2Code ;
    private String alpha3Code ;
    private List<String> borders ;

    public long getPopulation() {
        return population;
    }

    public String getSubregion() {
        return subregion;
    }

    public String getRegion() {
        return region;
    }

    public String getCapital() {
        return capital;
    }

    public String getName() {
        return name;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public List<String> getBorders() {
        return borders;
    }

    @Override
    public String toString() {
        return "CountryModel{" +
                "name='" + name + '\'' +
                ", subregion='" + subregion + '\'' +
                ", region='" + region + '\'' +
                ", capital='" + capital + '\'' +
                ", population=" + population +
                ", alpha2Code='" + alpha2Code + '\'' +
                ", alpha3Code='" + alpha3Code + '\'' +
                ", borders=" + borders +
                '}';
    }
}
