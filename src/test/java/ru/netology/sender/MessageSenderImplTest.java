package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderImplTest {

    private MessageSenderImpl sender;

//    @After
//    public void afterTest() {
//        sender = null;
//    }

    @Test
    public void sendTestRussianIfRus() {
        String testIP = "1.2.3.4";

        GeoServiceImpl geo = Mockito.mock(GeoServiceImpl.class);
        LocalizationServiceImpl loc = Mockito.mock(LocalizationServiceImpl.class);

        Mockito.when(geo.byIp(testIP)).thenReturn(new Location("Moscow", Country.RUSSIA, "", 1));
        Mockito.when(loc.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("x-real-ip", testIP);
        sender = new MessageSenderImpl(geo, loc);
        // when
        boolean isRus = sender.send(headers).matches(".*[а-яА-Я]+.*");
        //then
        Assertions.assertTrue(isRus);
    }

    @Test
    public void sendTestEnglishIfUSA() {
        String testIP = "5.6.7.8";

        GeoServiceImpl geo = Mockito.mock(GeoServiceImpl.class);
        LocalizationServiceImpl loc = Mockito.mock(LocalizationServiceImpl.class);

        Mockito.when(geo.byIp(testIP)).thenReturn(new Location("New York", Country.USA, "", 1));
        Mockito.when(loc.locale(Country.USA)).thenReturn("Welcome");

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("x-real-ip", testIP);
        sender = new MessageSenderImpl(geo, loc);
        // when
        boolean isRus = sender.send(headers).matches(".*[a-zA-Z]+.*");
        //then
        Assertions.assertTrue(isRus);
    }

}
