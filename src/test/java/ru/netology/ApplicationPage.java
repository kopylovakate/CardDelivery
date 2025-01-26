package ru.netology;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;


public class ApplicationPage {

    public SelenideElement cityField = $x("//*[@data-test-id='city']//input");
    public SelenideElement dateField = $x("//*[@data-test-id='date']//input");
    public SelenideElement firstLastNameField = $x("//*[@data-test-id='name']//input");
    public SelenideElement phoneNumberField = $x("//*[@data-test-id='phone']//input");
    public String date;

    private String generateDate(int daysToAdd, String pattern) {
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern(pattern));
    }

    private void selectCity(String city) {
        String firstLetters = city.substring(0, 2);
        cityField.setValue(firstLetters);
        $x("//*[text() = '" + city + "']").click();
    }

    private void selectDate(int daysToAdd) {
        dateField.click();
        date = generateDate(daysToAdd, "dd.MM.yyyy");
        if (!generateDate(daysToAdd, "MM").equals(generateDate(0, "MM"))) {
            $x("//*[contains(@class,'calendar__arrow')][@data-step='1'][@role='button']").click();
        }
        $$x("//*[@data-day]").findBy(Condition.text(generateDate(daysToAdd, "d"))).click();
    }

    public void fillForm(String city, int daysToAdd, String name, String phone) {
        if (!city.equals("") && $x("//*[text() = '" + city + "']").isDisplayed()) {
            selectCity(city);
        } else {
            cityField.setValue(city);
        }
        selectDate(daysToAdd);
        firstLastNameField.setValue(name);
        phoneNumberField.setValue(phone);
    }




}
