package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception
    {
        String input1 = "12345";
        String input2 = "abc123";

        System.out.println("Input 1 contains only numbers: " + containsOnlyNumbers(input1));
        System.out.println("Input 2 contains only numbers: " + containsOnlyNumbers(input2));

        boolean result = MyBot.validYear(input2);
        System.out.println(result);
        String number = numbersInformation("42" , "trivia") ;
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new MyBot());

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    public static String countryInformation(String countryName) throws Exception {
        String value;
        try {
            value = null;
            String url = Utils.COUNTRY_URL_API + countryName;
            GetRequest getRequest1 = Unirest.get(url);
            HttpResponse<String> response = getRequest1.asString();
            String json = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            List<CountryModel> countryModel = objectMapper.readValue(json, new TypeReference<>() {
            });
            for (CountryModel countryModel1 : countryModel) {
                System.out.println(countryModel1.getCapital());
                value = countryModel1.getCapital();
            }

        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    public static String numbersInformation(String number, String type) throws Exception {
        String numberValue = null;
        try {
            // Replace with the desired type (e.g., "trivia", "math", "year", etc.)

            URL url = new URL("http://numbersapi.com/" + number + "/" + type);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            numberValue = response.toString();

            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numberValue ;
    }
    public static boolean containsOnlyNumbers(String text) {
        String regex = "^[0-9]+$";
        return text.matches(regex);
    }

}

