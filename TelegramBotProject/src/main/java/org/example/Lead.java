package org.example;

import org.checkerframework.common.returnsreceiver.qual.This;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class Lead {
    private long chatId;
    private String name;
    private int choice ;
    private int countryChoice ;
    private int choice1 ;
    private String phone ;
    public  static final int KNOWN_CHOICE = 0 ;

    public  static final int INFORMATION_ABOUT_NUMBERS = 1 ;
    public  static final int INFORMATION_ABOUT_COUNTRIES = 2 ;
    public  static final int WEATHER_INFORMATION = 3 ;
    public  static final int YEAR = 4 ;
    public  static final int DATA = 5 ;
    public  static final int MATH = 6 ;
    public  static final int TRIVIA = 7 ;
    public  static final int CAPITAL = 8 ;
    public  static final int SUBREGION = 9 ;
    public  static final int POPULATION = 10 ;
    public  static final int REGION = 11 ;


    public Lead(long chatId)
    {
        this.chatId = chatId;
        this.choice = KNOWN_CHOICE ;
        this.choice1 = KNOWN_CHOICE ;
    }

    public boolean isChoiceKnown ()
    {
        return this.choice != KNOWN_CHOICE ;
    }
    public boolean isChoice1Known ()
    {
        return this.choice1 != KNOWN_CHOICE ;
    }
    public boolean isCountryChoiceKnown ()
    {
        return this.choice1 != KNOWN_CHOICE ;
    }

    public boolean isNumbersInformation ()
    {
        return this.choice == INFORMATION_ABOUT_NUMBERS ;
    }
    public boolean isChosenYear ()
    {
        return this.choice1 == YEAR ;
    }
    public boolean isDateSelected ()
    {
        return this.choice1 == DATA ;
    }
    public boolean isChosenTrivia ()
    {
        return this.choice1 == TRIVIA ;
    }
    public boolean isChosenMath ()
    {
        return this.choice1 == MATH ;

    }
    public boolean isChosenCapital ()
    {
        return this.countryChoice == CAPITAL;
    }
    public boolean isChosenSubregion ()
    {
        return this.countryChoice == SUBREGION ;
    }
    public boolean isChosenPopulation ()
    {
        return this.countryChoice == POPULATION ;
    }
    public boolean isChosenRegion ()
    {
        return this.countryChoice == REGION ;
    }


    public boolean isCountriesInformation ()
    {
        return this.choice == INFORMATION_ABOUT_COUNTRIES ;
    }

    public boolean isWeatherInformation ()
    {
        return this.choice == WEATHER_INFORMATION ;
    }

    public void Interest(String status)
    {
        if (status.equals("N"))
        {
            this.choice = INFORMATION_ABOUT_NUMBERS ;
        }
        else if (status.equals("C"))
        {
          this.choice = INFORMATION_ABOUT_COUNTRIES ;
        }
        else if (status.equals("W"))
        {
            this.choice = WEATHER_INFORMATION;
        }


    }
    public void numbersChoose (String status)
    {
        if (status.equals("T"))
    {
        this.choice1 = TRIVIA;
    }
    else if (status.equals("M"))
    {
        this.choice1 = MATH;
    }
    else if (status.equals("D"))
    {
        this.choice1 = DATA;
    }
    else if (status.equals("Y"))
    {
        this.choice1 = YEAR;
    }

    }

    public void CountryChoice (String status)
    {
        if (status.equals("Capital"))
        {
            this.countryChoice = CAPITAL;
        }
        else if (status.equals("Subregion"))
        {
            this.countryChoice = SUBREGION ;
        }
        else if (status.equals("Population"))
        {
            this.countryChoice = POPULATION ;
        }
        else
        {
            this.countryChoice = REGION ;
        }
    }
}


