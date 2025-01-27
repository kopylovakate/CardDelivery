package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;


public class CardTask2Test {

    private ApplicationPage applicationPage = new ApplicationPage();

        @BeforeEach
        void openWebpage() {
            Selenide.open("http://localhost:9999/");
        }

        @Test
        void validTest() {
            applicationPage.fillForm("Москва", 7, "Мария Попова", "+7281234567");
            $x("//*[@data-test-id='agreement']").click();
            $x("//button[descendant::*[*[contains(text(),'Забронировать')]]]").click();

            $(Selectors.withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(15));
            $x("//*[@data-test-id='notification']//*[starts-with(text(),'Встреча успешно забронирована на')]").
                    should(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.text(applicationPage.date));
        }



        @Test
        void emptyNameLineTest() {
            applicationPage.fillForm("Москва", 7, "", "+79281234567");
            $x("//*[@data-test-id='agreement']").click();
            $x("//button[descendant::*[*[contains(text(),'Забронировать')]]]").click();

            $x("//*[contains(@class, 'input_invalid')][@data-test-id='name']//*[contains(@class, 'input__sub')]")
                    .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
        }


        @Test
        void emptyPhoneLineTest() {
            applicationPage.fillForm("Москва", 7, "Мария Попова", "");
            $x("//*[@data-test-id='agreement']").click();
            $x("//button[descendant::*[*[contains(text(),'Забронировать')]]]").click();

            $x("//*[contains(@class, 'input_invalid')][@data-test-id='phone']//*[contains(@class, 'input__sub')]")
                    .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
        }


        @Test
        void lineCityInvalidInputTest() {
            applicationPage.fillForm("Москва23", 7, "Мария Попова", "+79281234567");
            $x("//*[@data-test-id='agreement']").click();
            $x("//button[descendant::*[*[contains(text(),'Забронировать')]]]").click();

            $x("//*[contains(@class, 'input_invalid')][@data-test-id='city']//*[contains(@class, 'input__sub')]")
                    .shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
        }

    @Test
    void linePhoneInvalidInputTest() {
        applicationPage.fillForm("Москва", 7, "Мария Попова", "89281234567");
        $x("//*[@data-test-id='agreement']").click();
        $x("//button[descendant::*[*[contains(text(),'Забронировать')]]]").click();

        $x("//*[contains(@class, 'input_invalid')][@data-test-id='phone']//*[contains(@class, 'input__sub')]")
                .shouldHave(Condition.text("Телефон указан неверно"));
    }

    @Test
    void emptyCityLineTest() {
        applicationPage.fillForm("", 7, "Мария Попова", "+79281234567");
        $x("//*[@data-test-id='agreement']").click();
        $x("//button[descendant::*[*[contains(text(),'Забронировать')]]]").click();

        $x("//*[contains(@class, 'input_invalid')][@data-test-id='city']//*[contains(@class, 'input__sub')]")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }


        @Test
        void lineNameInvalidInputTest() {
            applicationPage.fillForm("Москва", 7, "Maria Popova", "+79281234567");
            $x("//*[@data-test-id='agreement']").click();
            $x("//button[descendant::*[*[contains(text(),'Забронировать')]]]").click();

            $x("//*[contains(@class, 'input_invalid')][@data-test-id='name']//*[contains(@class, 'input__sub')]")
                    .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        }


        @Test
        void agreementTest() {
            applicationPage.fillForm("Москва", 7, "Мария Попова", "+79281234567");
            $x("//button[descendant::*[*[contains(text(),'Забронировать')]]]").click();

            $x("//*[@data-test-id='agreement']").
                    should(Condition.visible);
        }
    }

