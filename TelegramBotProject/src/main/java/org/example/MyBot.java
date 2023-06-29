package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.print.PrinterException;
import java.util.Calendar;

import java.util.*;

public class MyBot extends TelegramLongPollingBot {
    private final String BOT_TOKEN = "6171486691:AAFBLTw-Wu63jjdmzjGsuj00ltqtAlLAaVo";
    private final String BOT_USERNAME = "shiraAharoniBot";
    private Map<Long, Lead> LeadMap;
    private List<Long> chatId;


    public MyBot() {
        this.chatId = new ArrayList<>();
        this.LeadMap = new HashMap<>();
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
    private void sendInitialMessage(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        InlineKeyboardButton NumbersButton = new InlineKeyboardButton("Number's Information");
        NumbersButton.setCallbackData("N");
        InlineKeyboardButton CountryButton = new InlineKeyboardButton("Country's Information");
        CountryButton.setCallbackData("C");
        InlineKeyboardButton WeatherButton = new InlineKeyboardButton("Weather data");
        WeatherButton.setCallbackData("W");
        List<InlineKeyboardButton> topRow = Arrays.asList(CountryButton, WeatherButton);
        List<InlineKeyboardButton> bottomRow = Collections.singletonList(NumbersButton);
        List<List<InlineKeyboardButton>> keyboard = Arrays.asList(topRow, bottomRow);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        /*String capital;
        try {
            capital = Main.countryInformation("Israel");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

        //sendMessage.setText(capital);
       /*/ try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        sendMessage.setText("What are you interested in?");
        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onUpdateReceived(Update update) {
        long chatId = this.getChatId(update);
        Lead lead = this.LeadMap.get(chatId);
        SendMessage sendMessage = new SendMessage();
        if (lead == null) {
            sendInitialMessage(chatId);
            lead = new Lead(chatId);
            this.LeadMap.put(chatId, lead);
        } else {
            if (!lead.isChoiceKnown()) {
                lead.Interest(update.getCallbackQuery().getData());
                //SendMessage sendMessage = new SendMessage() ;
                sendMessage.setChatId(chatId);
                if (lead.isNumbersInformation()) {
                    sendNumberMessage(chatId);
                    if (!lead.isChoice1Known())
                    {
                        lead.numbersChoose(update.getCallbackQuery().getData());
                        sendMessage.setChatId(chatId);
                        // Handle button click based on buttonData value
                        if (lead.isChosenMath()) {
                            System.out.println("m");
                            // Button 1 was clicked
                        } else if (lead.isDateSelected()) {
                            System.out.println("d");
                            // Button 2 was clicked
                        } else if (lead.isChosenTrivia()) {
                            System.out.println("t");
                            // Button 3 was clicked
                        }
                        else if ((lead.isChosenYear()))
                        {
                            sendMessage.setChatId(chatId);
                            sendMessage.setText("Please enter a year about which you would like to receive information:");

                            try {
                                execute(sendMessage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (update.hasMessage()) {
                                Message message = update.getMessage();
                                String response = message.getText();
                                System.out.println(response);

                            }
                    }

                }
            } else if (lead.isCountriesInformation()) {
                try {
                    sendCountryMessage(chatId);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (!lead.isCountryChoiceKnown()) {
                    if (update.hasCallbackQuery()) {
                        CallbackQuery callbackQuery = update.getCallbackQuery();
                        long chatId1 = callbackQuery.getMessage().getChatId();
                        String buttonClicked = callbackQuery.getData();

                        // Handle the selected button based on the callback data
                        if (buttonClicked.equals("Capital")) {
                            sendMessage.setText("You clicked on the Capital button");
                            if (update.hasMessage() && update.getMessage().hasText())
                            {
                                // Get the received message and relevant information
                                String messageText = update.getMessage().getText();
                                System.out.println(messageText);
                            }
                        } else if (buttonClicked.equals("Subregion")) {
                            sendMessage.setText("You clicked on the Subregion button");
                        } else if (buttonClicked.equals("Region")) {
                            sendMessage.setText("You clicked on the Region button");
                        } else if (buttonClicked.equals("Population")) {
                            sendMessage.setText("You clicked on the Population button");
                        }

                        sendMessage.setChatId(chatId1);
                        try {
                            execute(sendMessage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    sendMessage.setText("Thank you.");
                }


              //  System.out.println(update.getCallbackQuery().getData());
            }
        }
    }
    }

    public String sendMessageToUser(long chatId, String message) {
        // Send a message to the user
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        // Validate the SendMessage object
        try {
            sendMessage.validate();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        // Retrieve the user's response
        String userResponse = null;

        // Implement the logic to retrieve the user's response
        // This can be done by using long polling or webhooks

        // Return the user's response
        return userResponse;
    }
    private void sendMessageWithButtonName(long chatId, String buttonName) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("You clicked on the button: " + buttonName);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public String handleUpdate(Update update)
    {
        String messageText = null ;
        if (update.hasMessage() && update.getMessage().hasText())
        {
            String chatId = update.getMessage().getChatId().toString();
            messageText = update.getMessage().getText();

            // Process the received message text
            System.out.println("Received message from user with chat ID: " + chatId);
            System.out.println("Message text: " + messageText);

            // Perform actions based on the received message
            // ...
        }
        return messageText ;
    }

    public void sendMessageToUser(String chatId, String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
    }

    private void send (SendMessage sendMessage)
    {
        try {
            execute(sendMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public long getChatId(Update update) {
        long chatId = 0;
        if (update.getMessage() != null) {
            chatId = update.getMessage().getChatId(); // if the user send me text!
        } else {
            chatId = update.getCallbackQuery().getMessage().getChatId(); // if the user button pressed!
        }
        return chatId ;
    }

    private void sendConfirmationMessage(String callbackQueryId, Long chatId) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackQueryId);
        answerCallbackQuery.setText("Thank you for connecting. Your phone number has been received.");

        try {
            execute(answerCallbackQuery);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Thank you for sharing your phone number.");

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendCountryMessage (long chatId ) throws Exception
    {
        SendMessage sendNumberMessage = new SendMessage();
        sendNumberMessage.setChatId(chatId);
        InlineKeyboardButton CapitalButton = new InlineKeyboardButton("Capital");
        CapitalButton.setCallbackData("Capital");
        InlineKeyboardButton SubregionButton = new InlineKeyboardButton("Subregion");
        SubregionButton.setCallbackData("Subregion");
        InlineKeyboardButton RegionButton = new InlineKeyboardButton("Region");
        RegionButton.setCallbackData("Region");
        InlineKeyboardButton PopulationButton = new InlineKeyboardButton("Population");
        PopulationButton.setCallbackData("Population");
        List<InlineKeyboardButton> topRow = Arrays.asList(SubregionButton, RegionButton);
        List<InlineKeyboardButton> lastRow = Arrays.asList(CapitalButton , PopulationButton);
        List<List<InlineKeyboardButton>> keyboard = Arrays.asList(topRow, lastRow);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        sendNumberMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendNumberMessage.setText("Choose a topic, about which you would like to receive information: ");
        try {
            execute(sendNumberMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNumberMessage(long chatId) {
        SendMessage sendNumberMessage = new SendMessage();
        sendNumberMessage.setChatId(chatId);
        InlineKeyboardButton triviaButton = new InlineKeyboardButton("Trivia");
        triviaButton.setCallbackData("T");
        InlineKeyboardButton mathButton = new InlineKeyboardButton("Math");
        mathButton.setCallbackData("M");
        InlineKeyboardButton dateButton = new InlineKeyboardButton("Data");
        dateButton.setCallbackData("D");
        InlineKeyboardButton yearButton = new InlineKeyboardButton("Year");
        yearButton.setCallbackData("Y");
        List<InlineKeyboardButton> topRow = Arrays.asList(mathButton, dateButton);
        List<InlineKeyboardButton> lastRow = Arrays.asList(triviaButton , yearButton);
        List<List<InlineKeyboardButton>> keyboard = Arrays.asList(topRow, lastRow);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        sendNumberMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendNumberMessage.setText("Choose a topic, about which you would like to receive information: ");
        try {
            execute(sendNumberMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean validYear (String year)
    {
        boolean isValid = false ;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if(containsOnlyNumbers(year))
        {
            int Year = Integer.parseInt(year);
            if ((Year > 0) && (Year <= currentYear))
            {
                isValid = true;
            }
        }
        return isValid ;
    }
    public static boolean containsOnlyNumbers(String text) {
        // Regular expression pattern to match only numbers
        String regex = "^[0-9]+$";

        // Return true if the text matches the pattern, false otherwise
        return text.matches(regex);
    }

}